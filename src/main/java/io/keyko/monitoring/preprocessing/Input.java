package io.keyko.monitoring.preprocessing;

import io.keyko.monitoring.config.StreamerConfig;
import io.keyko.monitoring.schemas.BlockRecord;
import io.keyko.monitoring.schemas.EventRecord;
import io.keyko.monitoring.schemas.LogRecord;
import io.keyko.monitoring.schemas.ViewRecord;
import io.keyko.monitoring.serde.Web3MonitoringSerdes;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;

public class Input {

  public static KStream<String, EventRecord> getEventStream(StreamerConfig configuration, StreamsBuilder builder) {
    return builder.stream(configuration.getEventTopic(), Consumed.with(Serdes.String(), Web3MonitoringSerdes.getEventSerde()));
  }

  public static KStream<String, ViewRecord> getViewStream(StreamerConfig configuration, StreamsBuilder builder) {
    return builder.stream(configuration.getViewTopic(), Consumed.with(Serdes.String(), Web3MonitoringSerdes.getViewSerde()));
  }

  public static KTable<String, BlockRecord> getBlockTable(StreamerConfig configuration, StreamsBuilder builder) {
    return builder.table(configuration.getBlockTopic(), Consumed.with(Serdes.String(), Web3MonitoringSerdes.getBlockSerde()));
  }

  public static KStream<String, LogRecord> getLogStream(StreamerConfig configuration, StreamsBuilder builder) {
    return builder.stream(configuration.getLogTopic(), Consumed.with(Serdes.String(), Web3MonitoringSerdes.getLogSerde()));
  }
}
