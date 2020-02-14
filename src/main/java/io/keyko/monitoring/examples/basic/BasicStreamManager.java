package io.keyko.monitoring.examples.basic;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.keyko.monitoring.config.StreamerConfig;
import io.keyko.monitoring.postprocessing.Output;
import io.keyko.monitoring.preprocessing.Filters;
import io.keyko.monitoring.preprocessing.Transformations;
import io.keyko.monitoring.schemas.BlockRecord;
import io.keyko.monitoring.schemas.EventRecord;
import io.keyko.monitoring.schemas.EventBlockRecord;
import io.keyko.monitoring.stream.BaseStreamManager;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;

public class BasicStreamManager extends BaseStreamManager {

  public BasicStreamManager(StreamerConfig config) {
    super(config);
  }

  @Override
  protected void processStreams(KStream<String, EventRecord> eventStream, KTable<String, BlockRecord> blockTable) {

    final KStream<String, EventRecord> eventAvroStream = Filters.filterConfirmed(eventStream);
    KStream<String, EventBlockRecord> eventBlockStream = Transformations.joinEventWithBlock(eventAvroStream, blockTable);
    Output.splitByEvent(eventBlockStream);

  }

  public static void main(final String[] args) throws Exception {

    Config config = args.length > 0 ? ConfigFactory.load(args[0]) : ConfigFactory.load();

    StreamerConfig streamerConfig = new StreamerConfig(config);
    new BasicStreamManager(streamerConfig).initStream();

  }
}
