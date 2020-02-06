package io.keyko.monitoring;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.keyko.monitoring.config.StreamerConfig;
import io.keyko.monitoring.stream.EventStreamManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class KeykoMonitoring {
  private static final Logger LOGGER = LogManager.getLogger(KeykoMonitoring.class);

  public static void main(final String[] args) throws Exception {

    Config config = args.length > 0 ? ConfigFactory.load(args[0]) : ConfigFactory.load();
    StreamerConfig streamerConfig = StreamerConfig.getInstance(config);

    LOGGER.info("Starting KeykoMonitoring...");
    new EventStreamManager(streamerConfig).initStream();
  }
}
