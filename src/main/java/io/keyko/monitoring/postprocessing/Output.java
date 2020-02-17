package io.keyko.monitoring.postprocessing;

import io.keyko.monitoring.schemas.EventBlockRecord;
import io.keyko.monitoring.schemas.ViewBlockRecord;
import io.keyko.monitoring.serde.Web3MonitoringSerdes;
import org.apache.kafka.common.serialization.Serde;
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
        "w3m-".concat(value.getDetails().getName().toLowerCase()).concat(suffix),
      Produced.with(Serdes.String(), Web3MonitoringSerdes.getEventBlockSerde())
    );
  }

  /**
   * Sending confirmed events to a topic corresponding with the name of the event.
   *
   * @param views Stream with the  events
   */
  public static void splitByView(KStream<String, ViewBlockRecord> views, String suffix) {
    views.to((key, value, recordContext) ->
        "w3m-".concat(value.getDetails().getContractName().toLowerCase()).concat("-").concat(value.getDetails().getName().toLowerCase()).concat(suffix),
      Produced.with(Serdes.String(), Web3MonitoringSerdes.getViewBlockSerde())
    );
  }

  public static void splitByView(KStream<String, ViewBlockRecord> views) {
    splitByView(views, "");
  }


}
