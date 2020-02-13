package io.keyko.monitoring.postprocessing;

import io.keyko.monitoring.schemas.EventBlock;
import io.keyko.monitoring.serde.Web3MonitoringSerdes;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

public class Output {

  /**
   * Sending events to a topic corresponding with the name of the event.
   *
   * @param events Stream with the confirmed events
   */
  public static void splitByEvent(KStream<String, EventBlock> events) {
    events.to((key, value, recordContext) ->
        value.getDetails().getName().toLowerCase(),
      Produced.with(Serdes.String(), Web3MonitoringSerdes.getEventBlockSerde())
    );
  }

}
