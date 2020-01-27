package io.keyko.monitoring.serde;

import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import net.consensys.eventeum.BlockEvent;
import net.consensys.eventeum.ContractEvent;
import net.consensys.eventeum.EventBlock;


public interface EventSerdes {
  SpecificAvroSerde<ContractEvent> eventAvroSerde = new SpecificAvroSerde<>();
  SpecificAvroSerde<BlockEvent> blockAvroSerde = new SpecificAvroSerde<BlockEvent>();
  SpecificAvroSerde<EventBlock> eventBlockAvroSerde = new SpecificAvroSerde<>();
}
