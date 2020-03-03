package io.keyko.monitoring.stream;

import io.keyko.monitoring.config.StreamerConfig;

import io.keyko.monitoring.preprocessing.Input;
import io.keyko.monitoring.preprocessing.TopicCreation;
import io.keyko.monitoring.schemas.BlockRecord;
import io.keyko.monitoring.schemas.EventRecord;
import io.keyko.monitoring.schemas.ViewRecord;
import io.keyko.monitoring.serde.Web3MonitoringSerdes;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;

import java.util.Properties;

public abstract class BaseStreamManager {

  private StreamerConfig configuration;
  protected StreamsBuilder builder;
  private static final Integer DEFAULT_THREADS = 1;
  private static final Integer DEFAULT_REPLICATION_FACTOR = 1;


  protected BaseStreamManager(StreamerConfig streamerConfig) {
    this.configuration = streamerConfig;
  }

  private Properties getStreamConfiguration() {

    Properties streamsConfiguration = new Properties();

    streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "web3monitoring-streamer");
    streamsConfiguration.put(StreamsConfig.CLIENT_ID_CONFIG, "web3monitoring-streamer");
    streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, this.configuration.getKafkaServer());
    streamsConfiguration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

    streamsConfiguration.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, DEFAULT_THREADS);
    streamsConfiguration.put(StreamsConfig.REPLICATION_FACTOR_CONFIG, DEFAULT_REPLICATION_FACTOR);

    // exactly_one implies COMMIT_INTERVAL_MS_CONFIG is set to 100 ms
    // streamsConfiguration.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, "exactly_once");
        /*
        streamsConfiguration.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 200);
        streamsConfiguration.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 10 * 1024 * 1024L);
        streamsConfiguration.put(StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG, WallclockTimestampExtractor.class);
         */

    return streamsConfiguration;

  }

  public void initStream() throws Exception {

    createTopics();
    KafkaStreams streams = createStreams();

    streams.cleanUp();
    // start processing
    streams.start();
    // Add shutdown hook to respond to SIGTERM and gracefully close Kafka Streams
    Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

  }

  protected void configureSerdes(String schemaRegistryUrl) {
    Web3MonitoringSerdes.configureSerdes(schemaRegistryUrl);
  }

  private KafkaStreams createStreams() {

    builder = new StreamsBuilder();
    configureSerdes(configuration.getSchemaRegistryUrl());

    final KTable<String, BlockRecord> blockTable = Input.getBlockTable(configuration, builder);
    KStream<String, EventRecord> eventStream = Input.getEventStream(configuration, builder);
    KStream<String, ViewRecord> viewStream = Input.getViewStream(configuration, builder);

    processStreams(eventStream, viewStream, blockTable);

    return new KafkaStreams(builder.build(), this.getStreamConfiguration());

  }

  private void createTopics(){
    TopicCreation.createTopics(configuration.getAllTopics(), configuration.getKafkaServer());

  }

  protected abstract void processStreams( KStream<String, EventRecord> eventStream, KStream<String, ViewRecord> viewStream,  KTable<String, BlockRecord> blockTable);


}
