package io.keyko.monitoring.app.defi;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.keyko.monitoring.app.celo.CeloConfig;
import io.keyko.monitoring.app.celo.CeloProcessor;
import io.keyko.monitoring.app.celo.CeloSerdes;
import io.keyko.monitoring.app.celo.model.AccountCreatedAggregation;
import io.keyko.monitoring.app.common.AppConfig;
import io.keyko.monitoring.app.common.AppSerdes;
import io.keyko.monitoring.postprocessing.Output;
import io.keyko.monitoring.preprocessing.Filters;
import io.keyko.monitoring.preprocessing.Transformations;
import io.keyko.monitoring.schemas.*;
import io.keyko.monitoring.serde.JsonPOJOSerde;
import io.keyko.monitoring.stream.BaseStreamManager;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;

import javax.security.auth.login.AppConfigurationEntry;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DefiStreamManager extends BaseStreamManager {

  AppConfig appConfig;

  public DefiStreamManager(AppConfig config) {
    super(config);
    this.appConfig = config;
  }

  @Override
  protected void configureSerdes(String schemaRegistryUrl) {
   AppSerdes.configureAllSerdes(schemaRegistryUrl);
  }

  @Override
  protected void processStreams(KStream<String, EventRecord> eventStream, KStream<String, ViewRecord> viewStream, KStream<String, LogRecord> logStream, KTable<String, BlockRecord> blockTable) {

    final KStream<String, EventRecord> eventAvroStream = Filters.filterConfirmed(eventStream);
    KStream<String, EventBlockRecord> eventBlockStream = Transformations.joinEventWithBlock(eventAvroStream, blockTable);
    Output.splitByEvent(eventBlockStream);


    KStream<String, ViewBlockRecord> viewBlockStream = Transformations.joinViewWithBlock(viewStream, blockTable);
    Output.splitByView(viewBlockStream);

  }

  public static void main(final String[] args) throws Exception {

    Config config = args.length > 0 ? ConfigFactory.load(args[0]) : ConfigFactory.load();

    AppConfig appConfig = new AppConfig(config);
    new DefiStreamManager(appConfig).initStream();

  }
}
