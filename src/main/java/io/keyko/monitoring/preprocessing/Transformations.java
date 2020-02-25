package io.keyko.monitoring.preprocessing;

import io.keyko.monitoring.schemas.*;
import io.keyko.monitoring.serde.Web3MonitoringSerdes;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.Joined;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;

import java.util.List;

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

  public static KStream<String, TimeSeriesRecord>  transformToTimeSeries(KStream<String, ViewBlockRecord> stream) {

    return stream.mapValues( viewBlock -> {

      List output = viewBlock.getDetails().getOutput();

      TimeSeriesRecord timeSeries = new TimeSeriesRecord();
      timeSeries.setContractName(viewBlock.getDetails().getContractName());
      timeSeries.setMethodName (viewBlock.getDetails().getName());
      timeSeries.setTimestamp(viewBlock.getDetailsBlock().getTimestamp());
      timeSeries.setBlockNumber(viewBlock.getDetailsBlock().getNumber());

      for (int i= 0; i< output.size(); i++) {

        Object object = output.get(i);
        NumberParameter numberParameter = object instanceof NumberParameter? (NumberParameter) object: null;
        StringParameter stringParameter = object instanceof StringParameter? (StringParameter) object: null;

        TimeSeriesParameter param = new TimeSeriesParameter();

        if (stringParameter!= null){
          param.setLabel(stringParameter.getName());
          param.setValue(stringParameter.getValue());
          param.setNumberValue(0l);
        }else if (numberParameter!= null){
          param.setLabel(numberParameter.getName());
          param.setValue(numberParameter.getValue());
          param.setNumberValue( numberParameter.getNumberValue());
        }
        switch(i) {
          case 0: timeSeries.setParam0(param); break;
          case 1: timeSeries.setParam1(param); break;
          case 2: timeSeries.setParam2(param); break;
          case 3: timeSeries.setParam3(param); break;
          case 4: timeSeries.setParam4(param); break;
          case 5: timeSeries.setParam5(param); break;
          case 6: timeSeries.setParam6(param); break;
          case 7: timeSeries.setParam7(param); break;
          case 8: timeSeries.setParam8(param); break;
          case 9: timeSeries.setParam9(param); break;
          default: break;
        }
      }
      return timeSeries;

    });

  }

}
