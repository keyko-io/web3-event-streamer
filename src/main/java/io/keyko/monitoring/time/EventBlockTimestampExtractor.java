package io.keyko.monitoring.time;

import io.keyko.monitoring.schemas.EventBlockRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

public class EventBlockTimestampExtractor implements TimestampExtractor {
  @Override
  public long extract(ConsumerRecord<Object, Object> record, long previousTimestamp) {

    if (record != null && record.value() != null) {

      if (record.value() instanceof EventBlockRecord) {
        return ((EventBlockRecord) record.value()).getDetailsBlock().getTimestamp();
      }

    }
    return record.timestamp();
  }
}
