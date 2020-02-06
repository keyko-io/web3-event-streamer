package io.keyko.monitoring.serde;

import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import io.keyko.monitoring.schemas.AlertEvent;
import io.keyko.monitoring.schemas.BlockEvent;
import io.keyko.monitoring.schemas.ContractEvent;
import io.keyko.monitoring.schemas.EventBlock;


public interface EventSerdes {
  final SpecificAvroSerde<ContractEvent> eventAvroSerde = new SpecificAvroSerde<>();
  final SpecificAvroSerde<BlockEvent> blockAvroSerde = new SpecificAvroSerde<BlockEvent>();
  final SpecificAvroSerde<EventBlock> eventBlockAvroSerde = new SpecificAvroSerde<>();
  final SpecificAvroSerde<AlertEvent> alertAvroSerde = new SpecificAvroSerde<>();
}
