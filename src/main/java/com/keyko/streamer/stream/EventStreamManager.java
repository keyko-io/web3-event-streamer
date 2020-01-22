package com.keyko.streamer.stream;

import com.keyko.streamer.config.StreamerConfig;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import net.consensys.eventeum.BlockEvent;
import net.consensys.eventeum.ContractEvent;
import net.consensys.eventeum.EventBlock;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;

public class EventStreamManager {

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


        final Map<String, String> serdeConfig =
                Collections.singletonMap(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG,
                        configuration.getSchemaRegistryUrl());


        final SpecificAvroSerde<ContractEvent> eventAvroSerde = new SpecificAvroSerde<>();
        eventAvroSerde.configure(serdeConfig, false);
        final KStream<String, ContractEvent> eventAvroStream =
                builder
                        .stream(configuration.getContractEventTopic(), Consumed.with(Serdes.String(), eventAvroSerde))
                        .filter ((key, event) -> event.getDetails().getStatus().toString().equalsIgnoreCase("CONFIRMED"));

        final SpecificAvroSerde<BlockEvent> blockAvroSerde = new SpecificAvroSerde<BlockEvent>();
        blockAvroSerde.configure(serdeConfig, false);
        final KTable<String, BlockEvent> blockAvroStream = builder.table(configuration.getBlockEventTopic(), Consumed.with(Serdes.String(), blockAvroSerde));


        KStream<String, EventBlock> eventBlockStream = eventAvroStream
                .selectKey((key, event) -> event.getDetails().getBlockHash())
                .join(blockAvroStream,
                        (event, block) -> {
                            EventBlock eventblock = new EventBlock();

                            eventblock.setDetails(event.getDetails());
                            eventblock.setDetailsBlock(block.getDetails());
                            eventblock.setId(event.getId());
                            eventblock.setRetries(event.getRetries());
                            eventblock.setType(event.getType());

                            return eventblock;
                        },
                        Joined.with(Serdes.String(), eventAvroSerde, blockAvroSerde)
                )
                .selectKey( (key, event) -> event.getId());


        final SpecificAvroSerde<EventBlock> eventBlockAvroSerde = new SpecificAvroSerde<>();
        eventBlockAvroSerde.configure(serdeConfig, false);

        eventBlockStream.to( (key, value, recordContext) ->
                        value.getDetails().getName().toLowerCase() + "_avro",
                Produced.with(Serdes.String(), eventBlockAvroSerde)
        );


        final KStream<String, EventBlock> valSigAvroStream =
                builder
                        .stream("validatorsignerauthorized_avro", Consumed.with(Serdes.String(), eventBlockAvroSerde))
                        .filter ((key, event) -> event.getDetails().getStatus().toString().equalsIgnoreCase("CONFIRMED"));



        valSigAvroStream.mapValues( event -> {

            String id = event.getId();
            System.out.println("VAlidator Signed Event");
            return event;
        });


        return new KafkaStreams(builder.build(), this.getStreamConfiguration());

    }

}
