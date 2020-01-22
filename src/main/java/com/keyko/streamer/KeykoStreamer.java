package com.keyko.streamer;

import com.keyko.streamer.config.StreamerConfig;
import com.keyko.streamer.stream.EventStreamManager;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class KeykoStreamer {

    public static void main(final String[] args) throws Exception {

        Config config = args.length > 0 ? ConfigFactory.load(args[0]): ConfigFactory.load();
        StreamerConfig streamerConfig = StreamerConfig.getInstance(config);

        new EventStreamManager(streamerConfig).initStream();

    }
}
