package io.keyko.monitoring.postprocessing;

import io.keyko.monitoring.schemas.EventBlockRecord;
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
  public static void splitByEvent(KStream<String, EventBlockRecord> events) {
    splitByEvent(events, "");
  }

  /**
   * Sending events to a topic corresponding with the name of the event and a suffix
   *
   * @param events Stream with the confirmed events
   */
  public static void splitByEvent(KStream<String, EventBlockRecord> events, String suffix) {
    events.to((key, value, recordContext) ->
        value.getDetails().getName().toLowerCase().concat(suffix),
      Produced.with(Serdes.String(), Web3MonitoringSerdes.getEventBlockSerde())
    );
  }

}
