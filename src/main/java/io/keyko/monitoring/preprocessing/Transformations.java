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
   * @param eventStream Stream with the confirmed events
   * @param blockStream Table with the blocks
   * @return KStream
   */
  public static KStream<String, EventBlockRecord> joinEventWithBlock(KStream<String, EventRecord> eventStream, KTable<String, BlockRecord> blockStream) {
    return eventStream
      .selectKey((key, event) -> event.getDetails().getBlockHash())
      .join(blockStream,
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
   * @param viewStream  Stream with the confirmed views
   * @param blockStream Table with the blocks
   * @return KStream
   * */

  public static KStream<String, ViewBlockRecord> joinViewWithBlock(KStream<String, ViewRecord> viewStream, KTable<String, BlockRecord> blockStream) {
    return viewStream
      .selectKey((key, view) -> view.getDetails().getBlockHash())
      .join(blockStream,
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
