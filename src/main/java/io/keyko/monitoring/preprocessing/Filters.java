package io.keyko.monitoring.preprocessing;

import io.keyko.monitoring.schemas.EventRecord;
import org.apache.kafka.streams.kstream.KStream;

public class Filters {

  public static KStream<String, EventRecord> filterConfirmed(KStream<String, EventRecord> contractEvents) {
    return contractEvents
      .filter((key, event) -> event.getStatus().toString().equalsIgnoreCase("CONFIRMED"));
  }
}
