package io.keyko.monitoring.serde;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import io.keyko.monitoring.schemas.AlertRecord;
import io.keyko.monitoring.schemas.BlockRecord;
import io.keyko.monitoring.schemas.EventRecord;
import io.keyko.monitoring.schemas.EventBlockRecord;

import java.util.Collections;
import java.util.Map;


public class Web3MonitoringSerdes {


  private final static SpecificAvroSerde<EventRecord> eventSerde = new SpecificAvroSerde<>();
  private final static SpecificAvroSerde<BlockRecord> blockSerde = new SpecificAvroSerde<>();
  private final static SpecificAvroSerde<EventBlockRecord> eventBlockSerde = new SpecificAvroSerde<>();
  private final static SpecificAvroSerde<AlertRecord> alertSerde = new SpecificAvroSerde<>();

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

  public static SpecificAvroSerde<EventRecord> getEventSerde() {
    return eventSerde;
  }

  public static SpecificAvroSerde<BlockRecord> getBlockSerde() {
    return blockSerde;
  }

  public static SpecificAvroSerde<EventBlockRecord> getEventBlockSerde() {
    return eventBlockSerde;
  }

  public static SpecificAvroSerde<AlertRecord> getAlertSerde() {
    return alertSerde;
  }



}
