package io.keyko.monitoring.examples.basic;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.keyko.monitoring.config.StreamerConfig;
import io.keyko.monitoring.postprocessing.Output;
import io.keyko.monitoring.preprocessing.Filters;
import io.keyko.monitoring.preprocessing.Transformations;
import io.keyko.monitoring.serde.Web3MonitoringSerdes;
import io.keyko.monitoring.schemas.*;
import io.keyko.monitoring.stream.BaseStreamManager;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;


public class BasicStreamManager extends BaseStreamManager {

  public BasicStreamManager(StreamerConfig config) {
    super(config);
  }

  @Override
  protected void processStreams(KStream<String, EventRecord> eventStream, KStream<String, ViewRecord> viewStream, KStream<String, LogRecord> logStream, KTable<String, BlockRecord> blockTable) {


    String ethScan = configuration.getEtherscanUrl();
    String contractAbiParams = configuration.getEtherscanGetContract();
    String url = ethScan.concat(contractAbiParams);

    KStream<String, EventRecord> eventFromLogStream = Transformations.transformLogToEvent(logStream,
                                                                          url,
                                                                          configuration.getEtherscanApikey(),
                                                                          configuration.getEtherscanSendNotMatchToTopic(),
                                                                          configuration.getLogNotMatchErrorTopic());

    eventFromLogStream.to("w3m-events-from-log", Produced.with(Serdes.String(), Web3MonitoringSerdes.getEventSerde()));

    final KStream<String, EventRecord> eventAvroStream = Filters.filterConfirmed(eventStream);
    KStream<String, EventBlockRecord> eventBlockStream = Transformations.joinEventWithBlock(eventAvroStream, blockTable);
    Output.splitByEvent(eventBlockStream);

    KStream<String, ViewBlockRecord> viewBlockStream = Transformations.joinViewWithBlock(viewStream, blockTable);
    Output.splitByView(viewBlockStream);

  }


  public static void main(final String[] args) throws Exception {

    Config config = args.length > 0 ? ConfigFactory.load(args[0]) : ConfigFactory.load();

    StreamerConfig streamerConfig = new StreamerConfig(config);
    new BasicStreamManager(streamerConfig).initStream();

  }
}
