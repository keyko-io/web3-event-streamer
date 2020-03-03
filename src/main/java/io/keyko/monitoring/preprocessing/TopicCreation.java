package io.keyko.monitoring.preprocessing;

import io.keyko.monitoring.config.StreamerConfig;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TopicCreation {

  public static void createTopics(List<String> topicNames, String kafkaServer) {
    Properties props = new Properties();
    props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
    AdminClient a = AdminClient.create(props);
    a.createTopics(convertToNewTopics(topicNames));
  }

  public static List<NewTopic> convertToNewTopics(List<String> topicNames) {
    List<NewTopic> topics = new ArrayList<NewTopic>();
    for (String i : topicNames) {
      topics.add(new NewTopic(i, 1, (short) 1));
    }
    return topics;
  }
}
