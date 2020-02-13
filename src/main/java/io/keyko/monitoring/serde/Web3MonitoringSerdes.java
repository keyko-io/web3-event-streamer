package io.keyko.monitoring.serde;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import io.keyko.monitoring.schemas.AlertEvent;
import io.keyko.monitoring.schemas.BlockEvent;
import io.keyko.monitoring.schemas.ContractEvent;
import io.keyko.monitoring.schemas.EventBlock;

import java.util.Collections;
import java.util.Map;


public class Web3MonitoringSerdes {


  private final static SpecificAvroSerde<ContractEvent> eventSerde = new SpecificAvroSerde<>();
  private final static SpecificAvroSerde<BlockEvent> blockSerde = new SpecificAvroSerde<>();
  private final static SpecificAvroSerde<EventBlock> eventBlockSerde = new SpecificAvroSerde<>();
  private final static SpecificAvroSerde<AlertEvent> alertSerde = new SpecificAvroSerde<>();

  protected static Map<String, String> serdeConfig;


  public static void configureSerdes(String schemaRegistryUrl) {

    serdeConfig = Collections.singletonMap(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);

    eventSerde.configure(serdeConfig, false);
    blockSerde.configure(serdeConfig, false);
    eventBlockSerde.configure(serdeConfig, false);
    alertSerde.configure(serdeConfig, false);
  }

  protected static void configureSerde(SpecificAvroSerde serde) {
    serde.configure(serdeConfig, false);
  }

  public static SpecificAvroSerde<ContractEvent> getEventSerde() {
    return eventSerde;
  }

  public static SpecificAvroSerde<BlockEvent> getBlockSerde() {
    return blockSerde;
  }

  public static SpecificAvroSerde<EventBlock> getEventBlockSerde() {
    return eventBlockSerde;
  }

  public static SpecificAvroSerde<AlertEvent> getAlertSerde() {
    return alertSerde;
  }



}
