package io.keyko.monitoring.services;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaProducerService {

  private static KafkaProducer<String, SpecificRecord> kafkaProducer = null;

  public static void init(String kafkaServer, String schemaRegistryUrl) {

    Properties properties = new Properties();
    // normal producer
    properties.setProperty("bootstrap.servers", kafkaServer);
    properties.setProperty("key.serializer", StringSerializer.class.getName());
    properties.setProperty("value.serializer", KafkaAvroSerializer.class.getName());
    properties.setProperty("schema.registry.url", schemaRegistryUrl);

    kafkaProducer = new KafkaProducer<>(properties);

  }

  public static KafkaProducer<String, SpecificRecord> getKafkaProducer(String kafkaServer, String schemaRegistryUrl){
    if (kafkaProducer==null)
      init(kafkaServer, schemaRegistryUrl);

    return kafkaProducer;
  }

  public static KafkaProducer<String, SpecificRecord> getKafkaProducer(){
    return kafkaProducer;
  }

  public static void send(String topic, String key, SpecificRecord record){
    ProducerRecord<String, SpecificRecord> producerRecord = new ProducerRecord<>(topic, key, record);
    getKafkaProducer().send(producerRecord);
  }




}
