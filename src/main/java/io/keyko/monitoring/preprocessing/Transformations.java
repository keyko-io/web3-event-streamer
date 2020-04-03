package io.keyko.monitoring.preprocessing;

import io.keyko.monitoring.exceptions.EventFromLogException;
import io.keyko.monitoring.schemas.*;
import io.keyko.monitoring.serde.Web3MonitoringSerdes;
import io.keyko.monitoring.services.EventLogService;
import io.keyko.monitoring.services.KafkaProducerService;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.Joined;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Transformations {

  private static Logger log = Logger.getLogger(Transformations.class);

  /**
   * Join the events with the corresponding block to track the timestamp of mining.
   *
   * @param eventStream Stream with the confirmed events
   * @param blockStream Table with the blocks
   * @return KStream
   */
  public static KStream<String, EventBlockRecord> joinEventWithBlock(KStream<String, EventRecord> eventStream, KTable<String, BlockRecord> blockStream) {
    return eventStream
      .selectKey((key, event) -> event.getBlockHash())
      .join(blockStream,
        (event, block) -> {
          EventBlockRecord eventblock = new EventBlockRecord();
          eventblock.setEvent(event);
          eventblock.setBlock(block);
          eventblock.setId(event.getId());
          eventblock.setRetries(event.getRetries());
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
   */

  public static KStream<String, ViewBlockRecord> joinViewWithBlock(KStream<String, ViewRecord> viewStream, KTable<String, BlockRecord> blockStream) {
    return viewStream
      .selectKey((key, view) -> view.getBlockHash())
      .join(blockStream,
        (view, block) -> {
          ViewBlockRecord viewBlock = new ViewBlockRecord();
          viewBlock.setId(view.getId());
          viewBlock.setRetries(view.getRetries());
          viewBlock.setBlock(block);
          viewBlock.setView(view);
          return viewBlock;
        },
        Joined.with(Serdes.String(), Web3MonitoringSerdes.getViewSerde(), Web3MonitoringSerdes.getBlockSerde())
      )
      .selectKey((key, view) -> view.getId())
      ;

  }

  /**
   * Transform the ViewBlock records into a TimeSeriesRecord
   *
   * @param stream
   * @return
   */
  public static KStream<String, TimeSeriesRecord> transformToTimeSeries(KStream<String, ViewBlockRecord> stream) {

    return stream.mapValues(viewBlock -> {

      List<Object> output = viewBlock.getView().getOutput();

      TimeSeriesRecord timeSeries = new TimeSeriesRecord();
      timeSeries.setContractName(viewBlock.getView().getContractName());
      timeSeries.setMethodName(viewBlock.getView().getName());
      timeSeries.setTimestamp(viewBlock.getBlock().getTimestamp());
      timeSeries.setBlockNumber(viewBlock.getBlock().getNumber());

      for (int i = 0; i < output.size(); i++) {

        Object object = output.get(i);
        NumberParameter numberParameter = object instanceof NumberParameter ? (NumberParameter) object : null;
        StringParameter stringParameter = object instanceof StringParameter ? (StringParameter) object : null;

        TimeSeriesParameter param = new TimeSeriesParameter();

        if (stringParameter != null) {
          param.setLabel(stringParameter.getName());
          param.setValue(stringParameter.getValue());
          param.setNumberValue(0L);
        } else if (numberParameter != null) {
          param.setLabel(numberParameter.getName());
          param.setValue(numberParameter.getValue());
          param.setNumberValue(numberParameter.getNumberValue());
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

  /**
   * Transform the EventBlock records into a TimeSeriesRecord
   *
   * @param stream
   * @return
   */
  public static KStream<String, TimeSeriesRecord> transformEventToTimeSeries(KStream<String, EventBlockRecord> stream) {
    return stream.mapValues(eventBlock -> {

      List<Object> output = eventBlock.getEvent().getIndexedParameters();
      output.addAll(eventBlock.getEvent().getNonIndexedParameters());

      TimeSeriesRecord timeSeries = new TimeSeriesRecord();
      timeSeries.setContractName(eventBlock.getEvent().getContractName());
      timeSeries.setMethodName(eventBlock.getEvent().getName());
      timeSeries.setTimestamp(eventBlock.getBlock().getTimestamp());
      timeSeries.setBlockNumber(eventBlock.getBlock().getNumber());

      for (int i = 0; i < output.size(); i++) {

        Object object = output.get(i);
        NumberParameter numberParameter = object instanceof NumberParameter ? (NumberParameter) object : null;
        StringParameter stringParameter = object instanceof StringParameter ? (StringParameter) object : null;

        TimeSeriesParameter param = new TimeSeriesParameter();

        if (stringParameter != null) {
          param.setLabel(stringParameter.getName());
          param.setValue(stringParameter.getValue());
          param.setNumberValue(0L);
        } else if (numberParameter != null) {
          param.setLabel(numberParameter.getName());
          param.setValue(numberParameter.getValue());
          param.setNumberValue(numberParameter.getNumberValue());
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

  public static  KStream<String, EventRecord> transformLogToEvent (KStream<String, LogRecord> logStream,  String getContractAbiUrl, String apiKey, Boolean sendErrorsToTopic, String errorTopic) {

    return logStream.flatMapValues(
      logRecord -> {
        EventRecord eventFromLog;

        // TODO Handle Exception
        try {
          log.debug("Getting eventRecord from LogRecord..");
          eventFromLog = EventLogService.getEventFromLog(logRecord, getContractAbiUrl, apiKey);
          log.debug("Get eventRecord from LogRecord OK!");
          return Arrays.asList(eventFromLog);
        } catch (EventFromLogException e) {
          log.debug("Error getting eventRecord from logRecord: " + e.getMessage());
          if (sendErrorsToTopic){
            log.debug("Sending logRecord to error topic in Kakfa");
            KafkaProducerService.send(errorTopic, logRecord.getId(), logRecord);
          }
        }

        return new ArrayList<>();
      }
    );

  }

  public static  KStream<String, EventRecord> transformLogToEvent (KStream<String, LogRecord> logStream,  String getContractAbiUrl, String apiKey) {
    return transformLogToEvent(logStream, getContractAbiUrl, apiKey, false, null);
  }

  public static KStream<String, LogRecordTopicsFlattened> flatLogs(KStream<String, LogRecord> logRecordKStream) {
    return logRecordKStream.mapValues(message -> {
        String topic0 = "";
        String topic1 = "";
        String topic2 = "";
        String topic3 = "";
        if (message.getTopics().size() > 0)
          topic0 = message.getTopics().get(0);
        if (message.getTopics().size() > 1)
          topic1 = message.getTopics().get(1);
        if (message.getTopics().size() > 2)
          topic2 = message.getTopics().get(2);
        if (message.getTopics().size() > 3)
          topic3 = message.getTopics().get(3);
        return LogRecordTopicsFlattened.newBuilder()
          .setBlockHash(message.getBlockHash())
          .setLogIndex(message.getLogIndex())
          .setBlockNumber(message.getBlockNumber())
          .setAddress(message.getAddress())
          .setTransactionHash(message.getTransactionHash())
          .setTopic0(topic0)
          .setTopic1(topic1)
          .setTopic2(topic2)
          .setTopic3(topic3)
          .setNodeName(message.getNodeName())
          .setData(message.getData())
          .setNetworkName(message.getNetworkName())
          .setStatus(LogFlattenedStatus.valueOf(message.getStatus().name()))
          .setId(message.getId())
          .setRetries(message.getRetries())
          .build();

      }
    );

  }

  }
