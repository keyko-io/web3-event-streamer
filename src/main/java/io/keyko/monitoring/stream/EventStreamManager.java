package io.keyko.monitoring.stream;

import io.keyko.monitoring.config.StreamerConfig;
import io.keyko.monitoring.model.AccountCreatedAggregation;
import io.keyko.monitoring.serde.EventSerdes;
import io.keyko.monitoring.serde.JsonPOJOSerde;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import net.consensys.eventeum.BlockEvent;
import net.consensys.eventeum.ContractEvent;
import net.consensys.eventeum.EventBlock;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;

import java.util.*;

public class EventStreamManager implements EventSerdes {

    private StreamerConfig configuration;
    private static final Integer DEFAULT_THREADS = 1;
    private static final Integer DEFAULT_REPLICATION_FACTOR = 1;


    public EventStreamManager(StreamerConfig streamerConfig) {
        this.configuration = streamerConfig;
    }

    private Properties getStreamConfiguration() {

        Properties streamsConfiguration = new Properties();

        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "eventStreamer");
        streamsConfiguration.put(StreamsConfig.CLIENT_ID_CONFIG, "eventStreamer-client");
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

        KafkaStreams streams = createStreams();

        streams.cleanUp();
        // start processing
        streams.start();
        // Add shutdown hook to respond to SIGTERM and gracefully close Kafka Streams
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

    }


    private KafkaStreams createStreams() {

        final StreamsBuilder builder = new StreamsBuilder();
        EventProcessor eventProcessor = new EventProcessor();


        final Map<String, String> serdeConfig =
                Collections.singletonMap(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG,
                        configuration.getSchemaRegistryUrl());


        eventAvroSerde.configure(serdeConfig, false);
        blockAvroSerde.configure(serdeConfig, false);
        eventBlockAvroSerde.configure(serdeConfig, false);


        KStream<String, ContractEvent> contractEvents = builder.stream(configuration.getContractEventTopic(), Consumed.with(Serdes.String(), eventAvroSerde));

        final KStream<String, ContractEvent> eventAvroStream = eventProcessor.filterConfirmed(contractEvents);

        final KTable<String, BlockEvent> blockAvroStream = builder.table(configuration.getBlockEventTopic(), Consumed.with(Serdes.String(), blockAvroSerde));

        KStream<String, EventBlock> eventBlockStream = eventProcessor.joinEventWithBlock(eventAvroStream, blockAvroStream, eventAvroSerde, blockAvroSerde);

        eventProcessor.splitTopics(eventBlockStream, eventBlockAvroSerde);


        List<String> accountsTopics = Arrays.asList("AccountCreated".toLowerCase(), "ValidatorSignerAuthorized".toLowerCase());//, "VoteSignerAuthorized".toLowerCase(), "AttestationSignerAuthorized".toLowerCase());
        KStream<String, AccountCreatedAggregation> accountsCreatedDayStream =  eventProcessor.accountDailyAggregation(accountsTopics, builder, eventBlockAvroSerde);
        accountsCreatedDayStream.to(configuration.getAccountsAggregationTopic(), Produced.with(Serdes.String(), new JsonPOJOSerde<AccountCreatedAggregation>(AccountCreatedAggregation.class)));

        return new KafkaStreams(builder.build(), this.getStreamConfiguration());

    }

}