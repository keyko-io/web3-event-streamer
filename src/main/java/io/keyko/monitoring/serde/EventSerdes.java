package io.keyko.monitoring.serde;

import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import io.keyko.monitoring.schemas.*;


public interface EventSerdes {
  final SpecificAvroSerde<EventRecord> eventAvroSerde = new SpecificAvroSerde<>();
  final SpecificAvroSerde<ViewRecord> viewAvroSerde = new SpecificAvroSerde<>();
  final SpecificAvroSerde<BlockRecord> blockAvroSerde = new SpecificAvroSerde<>();
  final SpecificAvroSerde<EventBlockRecord> eventBlockAvroSerde = new SpecificAvroSerde<>();
  final SpecificAvroSerde<ViewBlockRecord> viewBlockAvroSerde = new SpecificAvroSerde<>();
}
