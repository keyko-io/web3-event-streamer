package io.keyko.monitoring;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import io.keyko.monitoring.postprocessing.Output;
import io.keyko.monitoring.preprocessing.Transformations;
import io.keyko.monitoring.schemas.*;
import io.keyko.monitoring.serde.Web3MonitoringSerdes;
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

public class ProcessorsTest {


  Properties config = new Properties();
  private static final String SCHEMA_REGISTRY_SCOPE = ProcessorsTest.class.getName();
  private static final String MOCK_SCHEMA_REGISTRY_URL = "mock://" + SCHEMA_REGISTRY_SCOPE;


  public EventDetailsRecord transferDetails = new EventDetailsRecord("Transfer", "Transfer", "filter1", "default",
    Arrays.asList(new StringParameter("from", "address", "0xcCCD3999D5b421F906c4a35c0c95bcD533e1CFBb"), new StringParameter("to", "address", "0xC8FD77490A12F46709BffbCC0FCe35740Da8D860")),
    Collections.singletonList(new NumberParameter("amount", "uint256", "23873204128892815319", Long.valueOf(
      "23873204128892815319".substring(0, String.valueOf(Long.MAX_VALUE).length())))), "0x294d73910e7c1e7cd8f0bf341e513c0269a089b36c22c2ac006269eb59e6e6bf",
    "0", 15129L, "0x8ce40858181dccf410331c4b3edf0187ac7b887aeb5c6e0bce2dbc09635f470a", "0x5c7197E1147ebF98658A2a8Bc3D32BeBF1692829", ContractEventStatus.CONFIRMED,
    "0xddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef", "default",
    "0x294d73910e7c1e7cd8f0bf341e513c0269a089b36c22c2ac006269eb59e6e6bf-0x8ce40858181dccf410331c4b3edf0187ac7b887aeb5c6e0bce2dbc09635f470a-0");
  public EventRecord transferEvent = new EventRecord("0x294d73910e7c1e7cd8f0bf341e513c0269a089b36c22c2ac006269eb59e6e6bf-0x8ce40858181dccf410331c4b3edf0187ac7b887aeb5c6e0bce2dbc09635f470a-0",
    "CONTRACT_EVENT", transferDetails, 0);


  public EventDetailsRecord oracleReportedDetails = new EventDetailsRecord("OracleReported", "OracleReported", "filter2", "default",
    Collections.emptyList(),
    Arrays.asList(new StringParameter("token", "address", "0x5c7197E1147ebF98658A2a8Bc3D32BeBF1692829"), new StringParameter("oracle", "address", "0x0d473f73AAf1C2bf7EBd2be7196C71dBa6C1724b"), new NumberParameter("timestamp", "uint256", "1576176348", 1576176348L)
      , new NumberParameter("numerator", "uint256", "18299170121119875203", Long.valueOf(
        "18299170121119875203".substring(0, String.valueOf(Long.MAX_VALUE).length()))), new NumberParameter("denominator", "uint256", "18446744073709551616", Long.valueOf(
        "18446744073709551616".substring(0, String.valueOf(Long.MAX_VALUE).length())))), "0x294d73910e7c1e7cd8f0bf341e513c0269a089b36c22c2ac006269eb59e6e6bf",
    "0", 14750L, "0x73090d8e7bb7b2a2b550474c2c90e8059d9bfdcd752c5fc55af18f54debfb88d", "0x91061bF2F509AF76aa01F46E9F3E97577a5a80BA", ContractEventStatus.CONFIRMED,
    "0xdbf09271932e018b9c31e9988e4fbe3109fdd79d78f5d19a764dfb56035ed775", "default",
    "0x27bc3eda4e3eaae838dd44f4a9fd4564f4455c51e336daa4232afd4ea190f0f1-0x73090d8e7bb7b2a2b550474c2c90e8059d9bfdcd752c5fc55af18f54debfb88d-0");
  public BlockDetailsRecord blockOracleReportedDetails = new BlockDetailsRecord(14750L, "0x73090d8e7bb7b2a2b550474c2c90e8059d9bfdcd752c5fc55af18f54debfb88d", 1234L, "default");


  public BlockDetailsRecord blockTransferDetails = new BlockDetailsRecord(15129L, "0x8ce40858181dccf410331c4b3edf0187ac7b887aeb5c6e0bce2dbc09635f470a", 1234L, "default");
  public BlockRecord transferBlock = new BlockRecord("0x8ce40858181dccf410331c4b3edf0187ac7b887aeb5c6e0bce2dbc09635f470a", "BLOCK", blockTransferDetails, 0);

  public EventBlockRecord transferEventWithBlock = new EventBlockRecord("0x294d73910e7c1e7cd8f0bf341e513c0269a089b36c22c2ac006269eb59e6e6bf-0x8ce40858181dccf410331c4b3edf0187ac7b887aeb5c6e0bce2dbc09635f470a-0", "", transferDetails, blockTransferDetails, 0);

  public EventBlockRecord oracleReportedEventWithBlock = new EventBlockRecord("0x27bc3eda4e3eaae838dd44f4a9fd4564f4455c51e336daa4232afd4ea190f0f1-0x73090d8e7bb7b2a2b550474c2c90e8059d9bfdcd752c5fc55af18f54debfb88d-0", "", oracleReportedDetails, blockOracleReportedDetails, 0);

  public ViewDetailsRecord contractViewDetailsBalance = new ViewDetailsRecord("CeloGold-balanceOf",
    "1", "default", "default",
    Collections.singletonList(new NumberParameter("balance", "uint256", "12", 12L)),
    15129L, "0x8ce40858181dccf410331c4b3edf0187ac7b887aeb5c6e0bce2dbc09635f470a", "default", "default", "CeloGold-balanceOf-7c");

  public ViewRecord viewBalance = new ViewRecord("CeloGold-balanceOf-7c", "CONTRACT_VIEW", contractViewDetailsBalance, 0);


  final Serde<EventRecord> eventAvroSerde = Web3MonitoringSerdes.getEventSerde();
  final Serde<ViewRecord> viewAvroSerde = Web3MonitoringSerdes.getViewSerde();
  final Serde<BlockRecord> blockAvroSerde = Web3MonitoringSerdes.getBlockSerde();
  final Serde<EventBlockRecord> eventBlockAvroSerde = Web3MonitoringSerdes.getEventBlockSerde();
  final Serde<ViewBlockRecord> viewBlockAvroSerde = Web3MonitoringSerdes.getViewBlockSerde();
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
    viewAvroSerde.configure(conf, false);
    blockAvroSerde.configure(conf, false);
    eventBlockAvroSerde.configure(conf, false);
    viewBlockAvroSerde.configure(conf, false);
    builder = new StreamsBuilder();
  }

  @Test
  public void shouldJoinEventWithBlock() {

    KStream<String, EventRecord> transferEvents = builder.stream("confirmedEvents");
    KTable<String, BlockRecord> blockEvents = builder.table("block-events");
    Transformations.joinEventWithBlock(transferEvents, blockEvents).to("join");
    Topology topology = builder.build();

    TopologyTestDriver driver = new TopologyTestDriver(topology, config);

    TestInputTopic<String, BlockRecord> inputBlockTopic = driver.createInputTopic("block-events", new StringSerializer(), blockAvroSerde.serializer());
    TestInputTopic<String, EventRecord> inputTransferTopic = driver.createInputTopic("confirmedEvents", new StringSerializer(), eventAvroSerde.serializer());
    inputBlockTopic.pipeInput(transferBlock.getId(), transferBlock);
    inputTransferTopic.pipeInput(transferEvent.getId(), transferEvent);

    TestOutputTopic<String, EventBlockRecord> joinTopic = driver.createOutputTopic("join", new StringDeserializer(), eventBlockAvroSerde.deserializer());
    EventBlockRecord result = joinTopic.readValue();

    assertEquals(result.getDetailsBlock(), transferBlock.getDetails());
    assertEquals(result.getDetails(), transferEvent.getDetails());
    driver.close();
  }

  @Test
  public void splitConfirmedTopics() {
    KStream<String, EventBlockRecord> transferEvents = builder.stream("confirmedEvents");

    Output.splitByEvent(transferEvents);
    Topology topology = builder.build();
    TopologyTestDriver driver = new TopologyTestDriver(topology, config);

    TestInputTopic<String, EventBlockRecord> inputTopic = driver.createInputTopic("confirmedEvents", new StringSerializer(), eventBlockAvroSerde.serializer());

    inputTopic.pipeInput(transferEventWithBlock.getId(), transferEventWithBlock);
    inputTopic.pipeInput(oracleReportedEventWithBlock.getId(), oracleReportedEventWithBlock);

    TestOutputTopic<String, EventBlockRecord> transferTopic = driver.createOutputTopic("w3m-transfer", new StringDeserializer(), eventBlockAvroSerde.deserializer());
    TestOutputTopic<String, EventBlockRecord> oracleReportedTopic = driver.createOutputTopic("w3m-oraclereported", new StringDeserializer(), eventBlockAvroSerde.deserializer());


    assertEquals(transferTopic.readValue().getId(), transferEventWithBlock.getId());
    assertEquals(oracleReportedTopic.readValue().getId(), oracleReportedEventWithBlock.getId());
    driver.close();
  }


  @Test
  public void shouldJoinViewWithBlock() {

    KStream<String, ViewRecord> contractViewKStream = builder.stream("w3m-contract-views");
    KTable<String, BlockRecord> blockEvents = builder.table("w3m-block-events");
    Transformations.joinViewWithBlock(contractViewKStream, blockEvents).to("join");
    Topology topology = builder.build();

    TopologyTestDriver driver = new TopologyTestDriver(topology, config);

    TestInputTopic<String, BlockRecord> inputBlockTopic = driver.createInputTopic("w3m-block-events", new StringSerializer(), blockAvroSerde.serializer());
    TestInputTopic<String, ViewRecord> inputViewTopic = driver.createInputTopic("w3m-contract-views", new StringSerializer(), viewAvroSerde.serializer());
    inputBlockTopic.pipeInput(transferBlock.getId(), transferBlock);
    inputViewTopic.pipeInput(viewBalance.getId(), viewBalance);

    TestOutputTopic<String, ViewBlockRecord> joinTopic = driver.createOutputTopic("join", new StringDeserializer(), viewBlockAvroSerde.deserializer());
    ViewBlockRecord result = joinTopic.readValue();

    assertEquals(result.getDetailsBlock(), transferBlock.getDetails());
    assertEquals(result.getDetails(), viewBalance.getDetails());
    driver.close();
  }

}
