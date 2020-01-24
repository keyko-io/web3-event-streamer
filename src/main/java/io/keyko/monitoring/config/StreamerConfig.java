package io.keyko.monitoring.config;

import com.typesafe.config.Config;

public class StreamerConfig {

    private static final String KAFKA_BOOTSTRAP_SERVER = "kafka.bootstrap-server";
    private static final String SCHEMA_REGISTRY_URL = "schema-registry.url";
    private static final String CONTRACT_EVENT_TOPIC = "kafka.contract-event-topic";
    private static final String BLOCK_TOPIC = "kafka.block-topic";
    private static final String EVENT_BLOCK_TOPIC = "kafka.event-block-topic";
    private static final String FLAT_EVENT_TOPIC = "kafka.flat-event-topic";
    private static final String ACCOUNTS_AGGREGATION_TOPIC = "kafka.accounts-aggregation-topic";


    private StreamerConfig(){}

    private String kafkaServer;
    private String schemaRegistryUrl;
    private String contractEventTopic;
    private String blockEventTopic;
    private String eventBlockTopic;
    private String flatEventTopic;
    private String accountsAggregationTopic;


    private static StreamerConfig streamerConfig;


    public static StreamerConfig getInstance(Config config) {

        if (streamerConfig!=null)
            return streamerConfig;

        streamerConfig = new StreamerConfig();
        streamerConfig.setKafkaServer(config.getString(KAFKA_BOOTSTRAP_SERVER));
        streamerConfig.setSchemaRegistryUrl(config.getString(SCHEMA_REGISTRY_URL));
        streamerConfig.setContractEventTopic(config.getString(CONTRACT_EVENT_TOPIC));
        streamerConfig.setBlockEventTopic(config.getString(BLOCK_TOPIC));
        streamerConfig.setEventBlockTopic(config.getString(EVENT_BLOCK_TOPIC));
        streamerConfig.setFlatEventTopic(config.getString(FLAT_EVENT_TOPIC));
        streamerConfig.setAccountsAggregationTopic(config.getString(ACCOUNTS_AGGREGATION_TOPIC));


        return  streamerConfig;

    }


    public String getKafkaServer() {
        return kafkaServer;
    }

    public void setKafkaServer(String kafkaServer) {
        this.kafkaServer = kafkaServer;
    }

    public String getSchemaRegistryUrl() {
        return schemaRegistryUrl;
    }

    public void setSchemaRegistryUrl(String schemaRegistryUrl) {
        this.schemaRegistryUrl = schemaRegistryUrl;
    }

    public String getContractEventTopic() {
        return contractEventTopic;
    }

    public void setContractEventTopic(String contractEventTopic) {
        this.contractEventTopic = contractEventTopic;
    }

    public String getBlockEventTopic() {
        return blockEventTopic;
    }

    public void setBlockEventTopic(String blockEventTopic) {
        this.blockEventTopic = blockEventTopic;
    }

    public String getEventBlockTopic() {
        return eventBlockTopic;
    }

    public void setEventBlockTopic(String eventBlockTopic) {
        this.eventBlockTopic = eventBlockTopic;
    }

    public String getFlatEventTopic() {
        return flatEventTopic;
    }

    public void setFlatEventTopic(String flatEventTopic) {
        this.flatEventTopic = flatEventTopic;
    }

    public String getAccountsAggregationTopic() {
        return accountsAggregationTopic;
    }

    public void setAccountsAggregationTopic(String accountsAggregationTopic) {
        this.accountsAggregationTopic = accountsAggregationTopic;
    }

}
