package io.keyko.monitoring.examples.basic;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.keyko.monitoring.config.StreamerConfig;
import io.keyko.monitoring.postprocessing.Output;
import io.keyko.monitoring.preprocessing.Filters;
import io.keyko.monitoring.preprocessing.Transformations;
import io.keyko.monitoring.schemas.BlockEvent;
import io.keyko.monitoring.schemas.ContractEvent;
import io.keyko.monitoring.schemas.EventBlock;
import io.keyko.monitoring.stream.BaseStreamManager;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;

public class BasicProcessing extends BaseStreamManager {

  public BasicProcessing(StreamerConfig config) {
    super(config);
  }

  @Override
  protected void processStreams(KStream<String, ContractEvent> eventStream, KTable<String, BlockEvent> blockTable) {

    final KStream<String, ContractEvent> eventAvroStream = Filters.filterConfirmed(eventStream);
    KStream<String, EventBlock> eventBlockStream = Transformations.joinEventWithBlock(eventAvroStream, blockTable);
    Output.splitByEvent(eventBlockStream);

  }

  public static void main(final String[] args) throws Exception {

    Config config = args.length > 0 ? ConfigFactory.load(args[0]) : ConfigFactory.load();
    StreamerConfig streamerConfig = StreamerConfig.getInstance(config);

    StreamerConfig streamerConfig = new StreamerConfig(config);
    new BasicProcessing(streamerConfig).initStream();

  }
}
