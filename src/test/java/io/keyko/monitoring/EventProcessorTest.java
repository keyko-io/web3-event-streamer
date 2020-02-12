package io.keyko.monitoring;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import io.keyko.monitoring.stream.EventProcessor;
import io.keyko.monitoring.schemas.*;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class EventProcessorTest {


  Properties config = new Properties();
  private static final String SCHEMA_REGISTRY_SCOPE = EventProcessorTest.class.getName();
  private static final String MOCK_SCHEMA_REGISTRY_URL = "mock://" + SCHEMA_REGISTRY_SCOPE;


  public ContractEventDetails transferDetails = new ContractEventDetails("Transfer", "Transfer", "default",
    Arrays.asList(new StringParameter("from", "address", "0xcCCD3999D5b421F906c4a35c0c95bcD533e1CFBb"), new StringParameter("to", "address", "0xC8FD77490A12F46709BffbCC0FCe35740Da8D860")),
    Collections.singletonList(new NumberParameter("amount", "uint256", "23873204128892815319")), "0x294d73910e7c1e7cd8f0bf341e513c0269a089b36c22c2ac006269eb59e6e6bf",
    "0", "15129", "0x8ce40858181dccf410331c4b3edf0187ac7b887aeb5c6e0bce2dbc09635f470a", "0x5c7197E1147ebF98658A2a8Bc3D32BeBF1692829", ContractEventStatus.CONFIRMED,
    "0xddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef", "default",
    "0x294d73910e7c1e7cd8f0bf341e513c0269a089b36c22c2ac006269eb59e6e6bf-0x8ce40858181dccf410331c4b3edf0187ac7b887aeb5c6e0bce2dbc09635f470a-0");
  public ContractEvent transferEvent = new ContractEvent("0x294d73910e7c1e7cd8f0bf341e513c0269a089b36c22c2ac006269eb59e6e6bf-0x8ce40858181dccf410331c4b3edf0187ac7b887aeb5c6e0bce2dbc09635f470a-0",
    "CONTRACT_EVENT", transferDetails, 0);


  public ContractEventDetails oracleReportedDetails = new ContractEventDetails("OracleReported", "OracleReported", "default",
    Collections.emptyList(),
    Arrays.asList(new StringParameter("token", "address", "0x5c7197E1147ebF98658A2a8Bc3D32BeBF1692829"), new StringParameter("oracle", "address", "0x0d473f73AAf1C2bf7EBd2be7196C71dBa6C1724b"), new NumberParameter("timestamp", "uint256", "1576176348")
      , new NumberParameter("numerator", "uint256", "18299170121119875203"), new NumberParameter("denominator", "uint256", "18446744073709551616")), "0x294d73910e7c1e7cd8f0bf341e513c0269a089b36c22c2ac006269eb59e6e6bf",
    "0", "14750", "0x73090d8e7bb7b2a2b550474c2c90e8059d9bfdcd752c5fc55af18f54debfb88d", "0x91061bF2F509AF76aa01F46E9F3E97577a5a80BA", ContractEventStatus.CONFIRMED,
    "0xdbf09271932e018b9c31e9988e4fbe3109fdd79d78f5d19a764dfb56035ed775", "default",
    "0x27bc3eda4e3eaae838dd44f4a9fd4564f4455c51e336daa4232afd4ea190f0f1-0x73090d8e7bb7b2a2b550474c2c90e8059d9bfdcd752c5fc55af18f54debfb88d-0");
  public BlockDetails blockOracleReportedDetails = new BlockDetails("14750", "0x73090d8e7bb7b2a2b550474c2c90e8059d9bfdcd752c5fc55af18f54debfb88d", "1234", "default");


  public BlockDetails blockTransferDetails = new BlockDetails("15129", "0x8ce40858181dccf410331c4b3edf0187ac7b887aeb5c6e0bce2dbc09635f470a", "1234", "default");
  public BlockEvent transferBlock = new BlockEvent("0x8ce40858181dccf410331c4b3edf0187ac7b887aeb5c6e0bce2dbc09635f470a", "BLOCK", blockTransferDetails, 0);

  public EventBlock transferEventWithBlock = new EventBlock("0x294d73910e7c1e7cd8f0bf341e513c0269a089b36c22c2ac006269eb59e6e6bf-0x8ce40858181dccf410331c4b3edf0187ac7b887aeb5c6e0bce2dbc09635f470a-0", "", transferDetails, blockTransferDetails, 0);

  public EventBlock oracleReportedEventWithBlock = new EventBlock("0x27bc3eda4e3eaae838dd44f4a9fd4564f4455c51e336daa4232afd4ea190f0f1-0x73090d8e7bb7b2a2b550474c2c90e8059d9bfdcd752c5fc55af18f54debfb88d-0", "", oracleReportedDetails, blockOracleReportedDetails, 0);


  final Serde<ContractEvent> eventAvroSerde = new SpecificAvroSerde<>();
  final Serde<BlockEvent> blockAvroSerde = new SpecificAvroSerde<BlockEvent>();
  final Serde<EventBlock> eventBlockAvroSerde = new SpecificAvroSerde<>();
  private StreamsBuilder builder;


  @Before
  public void before() {
    config.setProperty(StreamsConfig.APPLICATION_ID_CONFIG, "event-streamer");
    config.setProperty(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "dummy:1234");
    config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde.class);
    config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, SpecificAvroSerde.class);
    config.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, MOCK_SCHEMA_REGISTRY_URL);

    Map<String, String> conf = new HashMap();
    conf.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, MOCK_SCHEMA_REGISTRY_URL);

    eventAvroSerde.configure(conf, false);
    blockAvroSerde.configure(conf, false);
    eventBlockAvroSerde.configure(conf, false);
    builder = new StreamsBuilder();
  }

  @Test
  public void shouldJoinEventWithBlock() {

    KStream<String, ContractEvent> transferEvents = builder.stream("confirmedEvents");
    KTable<String, BlockEvent> blockEvents = builder.table("block-events");
    new EventProcessor().joinEventWithBlock(transferEvents, blockEvents, eventAvroSerde, blockAvroSerde).to("join");
    Topology topology = builder.build();

    TopologyTestDriver driver = new TopologyTestDriver(topology, config);

    TestInputTopic<String, BlockEvent> inputBlockTopic = driver.createInputTopic("block-events", new StringSerializer(), blockAvroSerde.serializer());
    TestInputTopic<String, ContractEvent> inputTransferTopic = driver.createInputTopic("confirmedEvents", new StringSerializer(), eventAvroSerde.serializer());
    inputBlockTopic.pipeInput(transferBlock.getId(), transferBlock);
    inputTransferTopic.pipeInput(transferEvent.getId(), transferEvent);

    TestOutputTopic<String, EventBlock> joinTopic = driver.createOutputTopic("join", new StringDeserializer(), eventBlockAvroSerde.deserializer());
    EventBlock result = joinTopic.readValue();

    assertEquals(result.getDetailsBlock(), transferBlock.getDetails());
    assertEquals(result.getDetails(), transferEvent.getDetails());
    driver.close();
  }

  @Test
  public void splitConfirmedTopics() {
    KStream<String, EventBlock> transferEvents = builder.stream("confirmedEvents");

    new EventProcessor().splitTopics(transferEvents, eventBlockAvroSerde);
    Topology topology = builder.build();
    TopologyTestDriver driver = new TopologyTestDriver(topology, config);

    TestInputTopic<String, EventBlock> inputTopic = driver.createInputTopic("confirmedEvents", new StringSerializer(), eventBlockAvroSerde.serializer());

    inputTopic.pipeInput(transferEventWithBlock.getId(), transferEventWithBlock);
    inputTopic.pipeInput(oracleReportedEventWithBlock.getId(), oracleReportedEventWithBlock);

    TestOutputTopic<String, EventBlock> transferTopic = driver.createOutputTopic("transfer", new StringDeserializer(), eventBlockAvroSerde.deserializer());
    TestOutputTopic<String, EventBlock> oracleReportedTopic = driver.createOutputTopic("oraclereported", new StringDeserializer(), eventBlockAvroSerde.deserializer());


    assertEquals(transferTopic.readValue().getId(), transferEventWithBlock.getId());
    assertEquals(oracleReportedTopic.readValue().getId(), oracleReportedEventWithBlock.getId());
    driver.close();
  }
}
