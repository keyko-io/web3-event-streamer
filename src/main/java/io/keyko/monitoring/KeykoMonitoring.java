package io.keyko.monitoring;

import io.keyko.monitoring.config.StreamerConfig;
import io.keyko.monitoring.stream.EventStreamManager;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class KeykoMonitoring {

    public static void main(final String[] args) throws Exception {

        Config config = args.length > 0 ? ConfigFactory.load(args[0]): ConfigFactory.load();
        StreamerConfig streamerConfig = StreamerConfig.getInstance(config);

        new EventStreamManager(streamerConfig).initStream();

    }
}
