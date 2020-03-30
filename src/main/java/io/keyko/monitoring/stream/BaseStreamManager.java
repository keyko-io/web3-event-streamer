package io.keyko.monitoring.stream;

import io.keyko.monitoring.cache.CacheManagerProvider;
import io.keyko.monitoring.cache.InfinispanCacheProvider;
import io.keyko.monitoring.config.StreamerConfig;
import io.keyko.monitoring.preprocessing.Input;
import io.keyko.monitoring.preprocessing.TopicCreation;
import io.keyko.monitoring.schemas.BlockRecord;
import io.keyko.monitoring.schemas.EventRecord;
import io.keyko.monitoring.schemas.LogRecord;
import io.keyko.monitoring.schemas.ViewRecord;
import io.keyko.monitoring.serde.Web3MonitoringSerdes;
import io.keyko.monitoring.services.KafkaProducerService;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.log4j.Logger;

import java.net.URISyntaxException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public abstract class BaseStreamManager {

  protected StreamerConfig configuration;
  protected StreamsBuilder builder;
  private static final Integer DEFAULT_THREADS = 1;
  private static final Integer DEFAULT_REPLICATION_FACTOR = 1;
  private Logger log = Logger.getLogger(BaseStreamManager.class);


  protected BaseStreamManager(StreamerConfig streamerConfig) {
    this.configuration = streamerConfig;
  }

  private Properties getStreamConfiguration(String applicationId) {

    Properties streamsConfiguration = new Properties();

    streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId);
    streamsConfiguration.put(StreamsConfig.CLIENT_ID_CONFIG, applicationId);
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
    if (configuration.getKafkaCreateTopics()) createTopics();

    if (configuration.getEtherscanSendNotMatchToTopic())
      KafkaProducerService.init(configuration.getKafkaServer(), configuration.getSchemaRegistryUrl());

    /*
    try {
      CacheManagerProvider.initCacheManagerService("cache-serialization.xml", TimeUnit.HOURS, configuration.getCacheExpiryTime());
    } catch (URISyntaxException e) {
     log.error("Error initializing the CacheManager " + e.getMessage());
     throw e;
    }
*/

    InfinispanCacheProvider.initCacheManagerService(TimeUnit.HOURS, configuration.getCacheExpiryTime());

    KafkaStreams streams = createStreams();

    streams.cleanUp();
    // start processing
    streams.start();
    // Add shutdown hook to respond to SIGTERM and gracefully close Kafka Streams
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      try {
        streams.close();
        InfinispanCacheProvider.finishCacheManager();
      } catch (Exception e) {
        // ignored
      }
    }));

    log.warn("If your process stop just starting, review that the topics that your process is going to use are already created.");
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
    KStream<String, LogRecord> logStream = Input.getLogStream(configuration, builder);


    processStreams(eventStream, viewStream, logStream, blockTable);

    return new KafkaStreams(builder.build(), this.getStreamConfiguration(configuration.getApplicationId()));

  }

  private void createTopics() {
    TopicCreation.createTopics(configuration.getAllTopics(), configuration.getKafkaServer());
  }

  protected abstract void processStreams(KStream<String, EventRecord> eventStream, KStream<String, ViewRecord> viewStream, KStream<String, LogRecord> logStream, KTable<String, BlockRecord> blockTable);


}
