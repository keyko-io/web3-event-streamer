package io.keyko.monitoring.config;

import com.typesafe.config.Config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StreamerConfig {

  private static final String KAFKA_BOOTSTRAP_SERVER = "kafka.bootstrap-server";
  private static final String KAFKA_CREATE_TOPICS = "kafka.create-topics";
  private static final String SCHEMA_REGISTRY_URL = "schema-registry.url";
  private static final String EVENT_TOPIC = "kafka.topics.event-topic";
  private static final String VIEW_TOPIC = "kafka.topics.view-topic";
  private static final String BLOCK_TOPIC = "kafka.topics.block-topic";
  private static final String EVENT_BLOCK_TOPIC = "kafka.topics.event-block-topic";
  private static final String FLAT_EVENT_TOPIC = "kafka.topics.flat-event-topic";
  private static final String ALERTS_TOPIC = "kafka.topics.alert-topic";
  private static final String ALL_TOPICS = "kafka.topics";

  private String kafkaServer;
  private Boolean kafkaCreateTopics;
  private String schemaRegistryUrl;
  private String eventTopic;
  private String viewTopic;
  private String blockTopic;
  private String eventBlockTopic;
  private String flatEventTopic;
  private String alertsTopic;
  private List<String> topicList;


  public StreamerConfig(Config config) {

    this.setKafkaServer(config.getString(KAFKA_BOOTSTRAP_SERVER));
    this.setKafkaCreateTopics(config.getBoolean(KAFKA_CREATE_TOPICS));
    this.setSchemaRegistryUrl(config.getString(SCHEMA_REGISTRY_URL));
    this.setEventTopic(config.getString(EVENT_TOPIC));
    this.setViewTopic(config.getString(VIEW_TOPIC));
    this.setBlockTopic(config.getString(BLOCK_TOPIC));
    this.setEventBlockTopic(config.getString(EVENT_BLOCK_TOPIC));
    this.setFlatEventTopic(config.getString(FLAT_EVENT_TOPIC));
    this.setAlertsTopic(config.getString(ALERTS_TOPIC));
    this.setAllTopics(config.getConfig(ALL_TOPICS).root().unwrapped().values());
  }

  public String getKafkaServer() {
    return kafkaServer;
  }

  public void setKafkaServer(String kafkaServer) {
    this.kafkaServer = kafkaServer;
  }

  public Boolean getKafkaCreateTopics() {
    return kafkaCreateTopics;
  }

  public void setKafkaCreateTopics(Boolean kafkaCreateTopics) {
    this.kafkaCreateTopics = kafkaCreateTopics;
  }

  public String getSchemaRegistryUrl() {
    return schemaRegistryUrl;
  }

  public void setSchemaRegistryUrl(String schemaRegistryUrl) {
    this.schemaRegistryUrl = schemaRegistryUrl;
  }

  public String getEventTopic() {
    return eventTopic;
  }

  public void setEventTopic(String eventTopic) {
    this.eventTopic = eventTopic;
  }

  public String getViewTopic() {
    return viewTopic;
  }

  public void setViewTopic(String viewTopic) {
    this.viewTopic = viewTopic;
  }

  public String getBlockTopic() {
    return blockTopic;
  }

  public void setBlockTopic(String blockTopic) {
    this.blockTopic = blockTopic;
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

  public List<String> getAllTopics() {
    return topicList;
  }

  public void setAllTopics(Collection<Object> values) {
    List<String> list = new ArrayList<>();
    for (Object i : values) {
      list.add(i.toString());
    }
    this.topicList = list;
  }

}
