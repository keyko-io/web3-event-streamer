package io.keyko.monitoring.serde;

import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import io.keyko.monitoring.schemas.*;


public interface EventSerdes {
  final SpecificAvroSerde<ContractEvent> eventAvroSerde = new SpecificAvroSerde<>();
  final SpecificAvroSerde<ContractView> viewAvroSerde = new SpecificAvroSerde<>();
  final SpecificAvroSerde<BlockEvent> blockAvroSerde = new SpecificAvroSerde<BlockEvent>();
  final SpecificAvroSerde<EventBlock> eventBlockAvroSerde = new SpecificAvroSerde<>();
  final SpecificAvroSerde<ViewBlock> viewBlockAvroSerde = new SpecificAvroSerde<>();
}
