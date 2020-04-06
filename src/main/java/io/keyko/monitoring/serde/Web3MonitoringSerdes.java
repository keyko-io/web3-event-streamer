package io.keyko.monitoring.serde;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import io.keyko.monitoring.schemas.*;

import java.util.Collections;
import java.util.Map;


public class Web3MonitoringSerdes {


  private final static SpecificAvroSerde<EventRecord> eventSerde = new SpecificAvroSerde<>();
  private final static SpecificAvroSerde<BlockRecord> blockSerde = new SpecificAvroSerde<>();
  private final static SpecificAvroSerde<EventBlockRecord> eventBlockSerde = new SpecificAvroSerde<>();
  private final static SpecificAvroSerde<AlertRecord> alertSerde = new SpecificAvroSerde<>();
  private final static SpecificAvroSerde<ViewRecord> viewSerde = new SpecificAvroSerde<>();
  private final static SpecificAvroSerde<ViewBlockRecord> viewBlockSerde = new SpecificAvroSerde<>();
  private final static SpecificAvroSerde<TimeSeriesRecord> timeSeriesSerde = new SpecificAvroSerde<>();
  private final static SpecificAvroSerde<LogRecord> logSerde = new SpecificAvroSerde<>();
  private final static SpecificAvroSerde<LogRecordTopicsFlattened> logFlattenedSerde = new SpecificAvroSerde<>();
  private final static SpecificAvroSerde<FlatEventBlockRecord> flatEventBlockSerde = new SpecificAvroSerde<>();
  protected static Map<String, String> serdeConfig;


  public static void configureSerdes(String schemaRegistryUrl) {

    serdeConfig = Collections.singletonMap(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);

    eventSerde.configure(serdeConfig, false);
    blockSerde.configure(serdeConfig, false);
    eventBlockSerde.configure(serdeConfig, false);
    alertSerde.configure(serdeConfig, false);
    viewSerde.configure(serdeConfig, false);
    viewBlockSerde.configure(serdeConfig, false);
    timeSeriesSerde.configure(serdeConfig, false);
    logSerde.configure(serdeConfig, false);
    logFlattenedSerde.configure(serdeConfig, false);
    flatEventBlockSerde.configure(serdeConfig, false);
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

  public static SpecificAvroSerde<ViewRecord> getViewSerde() { return viewSerde; }

  public static SpecificAvroSerde<ViewBlockRecord> getViewBlockSerde() { return viewBlockSerde; }

  public static SpecificAvroSerde<TimeSeriesRecord> getTimeSeriesSerde() { return timeSeriesSerde; }

  public static SpecificAvroSerde<TimeSeriesRecord> getTimeSerieserde() { return timeSeriesSerde; }

  public static SpecificAvroSerde<LogRecord> getLogSerde() {
    return logSerde;
  }

  public static SpecificAvroSerde<LogRecordTopicsFlattened> getLogFlattenedSerde() {
    return logFlattenedSerde;
  }

  public static SpecificAvroSerde<FlatEventBlockRecord> getFlatEventBlockSerde() {
    return flatEventBlockSerde;
  }


}
