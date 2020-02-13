package io.keyko.monitoring.config;

import com.typesafe.config.Config;

public class StreamerConfig {

  private static final String KAFKA_BOOTSTRAP_SERVER = "kafka.bootstrap-server";
  private static final String SCHEMA_REGISTRY_URL = "schema-registry.url";
  private static final String CONTRACT_EVENT_TOPIC = "kafka.contract-event-topic";
  private static final String BLOCK_TOPIC = "kafka.block-topic";
  private static final String EVENT_BLOCK_TOPIC = "kafka.event-block-topic";
  private static final String FLAT_EVENT_TOPIC = "kafka.flat-event-topic";
  private static final String ALERTS_TOPIC = "kafka.alerts-topic";

  private String kafkaServer;
  private String schemaRegistryUrl;
  private String contractEventTopic;
  private String blockEventTopic;
  private String eventBlockTopic;
  private String flatEventTopic;
  private String alertsTopic;

  public StreamerConfig(Config config) {

    this.setKafkaServer(config.getString(KAFKA_BOOTSTRAP_SERVER));
    this.setSchemaRegistryUrl(config.getString(SCHEMA_REGISTRY_URL));
    this.setContractEventTopic(config.getString(CONTRACT_EVENT_TOPIC));
    this.setBlockEventTopic(config.getString(BLOCK_TOPIC));
    this.setEventBlockTopic(config.getString(EVENT_BLOCK_TOPIC));
    this.setFlatEventTopic(config.getString(FLAT_EVENT_TOPIC));
    this.setAlertsTopic(config.getString(ALERTS_TOPIC));

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

  public String getAlertsTopic() {
    return alertsTopic;
  }

  public void setAlertsTopic(String alertsTopic) {
    this.alertsTopic = alertsTopic;
  }

}
