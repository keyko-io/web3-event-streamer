package io.keyko.monitoring.preprocessing;

import io.keyko.monitoring.schemas.ContractEvent;
import org.apache.kafka.streams.kstream.KStream;

public class Filters {

  public static KStream<String, ContractEvent> filterConfirmed(KStream<String, ContractEvent> contractEvents) {
    return contractEvents
      .filter((key, event) -> event.getDetails().getStatus().toString().equalsIgnoreCase("CONFIRMED"));
  }
}
