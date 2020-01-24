package io.keyko.monitoring.serde;

import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import net.consensys.eventeum.BlockEvent;
import net.consensys.eventeum.ContractEvent;
import net.consensys.eventeum.EventBlock;


public interface EventSerdes {
  final SpecificAvroSerde<ContractEvent> eventAvroSerde = new SpecificAvroSerde<>();
  final SpecificAvroSerde<BlockEvent> blockAvroSerde = new SpecificAvroSerde<BlockEvent>();
  final SpecificAvroSerde<EventBlock> eventBlockAvroSerde = new SpecificAvroSerde<>();
}
