package io.keyko.monitoring.preprocessing;

import io.keyko.monitoring.schemas.BlockEvent;
import io.keyko.monitoring.schemas.ContractEvent;
import io.keyko.monitoring.schemas.EventBlock;
import io.keyko.monitoring.serde.Web3MonitoringSerdes;
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
  public static KStream<String, EventBlock> joinEventWithBlock(KStream<String, ContractEvent> eventAvroStream, KTable<String, BlockEvent> blockAvroStream) {
    return eventAvroStream
      .selectKey((key, event) -> event.getDetails().getBlockHash())
      .join(blockAvroStream,
        (event, block) -> {
          EventBlock eventblock = new EventBlock();

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
}
