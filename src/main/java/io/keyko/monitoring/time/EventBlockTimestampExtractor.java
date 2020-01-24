package io.keyko.monitoring.time;

import net.consensys.eventeum.EventBlock;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

public class EventBlockTimestampExtractor implements TimestampExtractor {
    @Override
    public long extract(ConsumerRecord<Object, Object> record, long previousTimestamp) {

        if (record!=null && record.value() != null) {

            if (record.value() instanceof EventBlock) {

                String ts = ((EventBlock)record.value()).getDetailsBlock().getTimestamp();
                // ts coming from eventeum is in seconds
                return (Long.valueOf(ts)) * 1000;
            }

        }
        return record.timestamp();
    }
}
