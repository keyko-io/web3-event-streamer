package io.keyko.monitoring;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.keyko.monitoring.config.StreamerConfig;
import io.keyko.monitoring.stream.EventStreamManager;

public class KeykoMonitoring {

  public static void main(final String[] args) throws Exception {

    Config config = args.length > 0 ? ConfigFactory.load(args[0]) : ConfigFactory.load();
    StreamerConfig streamerConfig = StreamerConfig.getInstance(config);

    new EventStreamManager(streamerConfig).initStream();

  }
}
