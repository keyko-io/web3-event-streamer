package io.keyko.monitoring.preprocessing;

import io.keyko.monitoring.config.StreamerConfig;
import io.keyko.monitoring.schemas.BlockEvent;
import io.keyko.monitoring.schemas.ContractEvent;
import io.keyko.monitoring.serde.Web3MonitoringSerdes;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;

public class Input {

  public static KStream<String, ContractEvent> getEventStream(StreamerConfig configuration, StreamsBuilder builder) {
    return builder.stream(configuration.getContractEventTopic(), Consumed.with(Serdes.String(), Web3MonitoringSerdes.getEventSerde()));
  }

  public static KTable<String, BlockEvent> getBlockTable(StreamerConfig configuration, StreamsBuilder builder) {
    return builder.table(configuration.getBlockEventTopic(), Consumed.with(Serdes.String(), Web3MonitoringSerdes.getBlockSerde()));
  }
}
