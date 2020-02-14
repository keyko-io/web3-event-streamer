package io.keyko.monitoring.preprocessing;

import io.keyko.monitoring.schemas.*;
import io.keyko.monitoring.serde.Web3MonitoringSerdes;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.Joined;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;

public class Transformations {

  /**
   * Join the events with the corresponding block to track the timestamp of mining.
   *
   * @param eventAvroStream Stream with the confirmed events
   * @param blockAvroStream Table with the blocks
   * @return KStream
   */
  public static KStream<String, EventBlockRecord> joinEventWithBlock(KStream<String, EventRecord> eventAvroStream, KTable<String, BlockRecord> blockAvroStream) {
    return eventAvroStream
      .selectKey((key, event) -> event.getDetails().getBlockHash())
      .join(blockAvroStream,
        (event, block) -> {
          EventBlockRecord eventblock = new EventBlockRecord();

          eventblock.setDetails(event.getDetails());
          eventblock.setDetailsBlock(block.getDetails());
          eventblock.setId(event.getId());
          eventblock.setRetries(event.getRetries());
          eventblock.setType(event.getType());

          return eventblock;
        },
        Joined.with(Serdes.String(), Web3MonitoringSerdes.getEventSerde(), Web3MonitoringSerdes.getBlockSerde())
      )
      .selectKey((key, event) -> event.getId());
  }

  /**
   * Join the views with the corresponding block to track the timestamp of mining.
   *
   * @param viewAvroStream  Stream with the confirmed views
   * @param blockAvroStream Table with the blocks
   * @return KStream
   * */

  public static KStream<String, ViewBlockRecord> joinViewWithBlock(KStream<String, ViewRecord> viewAvroStream, KTable<String, BlockRecord> blockAvroStream) {
    return viewAvroStream
      .selectKey((key, view) -> view.getDetails().getBlockHash())
      .join(blockAvroStream,
        (view, block) -> {
          ViewBlockRecord viewBlock = new ViewBlockRecord();

          viewBlock.setDetails(view.getDetails());
          viewBlock.setDetailsBlock(block.getDetails());
          viewBlock.setId(view.getId());
          viewBlock.setRetries(view.getRetries());
          viewBlock.setType(view.getType());
          return viewBlock;
        },
        Joined.with(Serdes.String(), Web3MonitoringSerdes.getViewSerde(), Web3MonitoringSerdes.getBlockSerde())
      )
      .selectKey((key, view) -> view.getId())
      ;

  }


}
