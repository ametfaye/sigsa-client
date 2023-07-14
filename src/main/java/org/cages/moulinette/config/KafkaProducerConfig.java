package org.cages.moulinette.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;


@Configuration
public class KafkaProducerConfig {

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
          ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "http://141.95.173.56:9092");
        configProps.put(
          ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,  StringSerializer.class);
        configProps.put(
          ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,  StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}

/*

KEY : REVP7H6E5574B5ZL

SECRET  : 4SPucNl9o6VsVC9PPwHRZzOMhuWYvpBYUcq91NxkCRX+4jjAUdc4h6SZXr7Mo10J

=== Confluent Cloud API key: lkc-v17yj0 ===

API key:
REVP7H6E5574B5ZL

API secret:
4SPucNl9o6VsVC9PPwHRZzOMhuWYvpBYUcq91NxkCRX+4jjAUdc4h6SZXr7Mo10J

Bootstrap server:
pkc-p11xm.us-east-1.aws.confluent.cloud:9092



# Required connection configs for Kafka producer, consumer, and admin
spring.kafka.properties.sasl.mechanism=PLAIN
spring.kafka.properties.bootstrap.servers=pkc-p11xm.us-east-1.aws.confluent.cloud:9092
spring.kafka.properties.sasl.jaas.config=org.apache.kafka.common.security.plain.PlainLoginModule required username='REVP7H6E5574B5ZL' password='4SPucNl9o6VsVC9PPwHRZzOMhuWYvpBYUcq91NxkCRX+4jjAUdc4h6SZXr7Mo10J';
spring.kafka.properties.security.protocol=SASL_SSL

# Best practice for higher availability in Apache Kafka clients prior to 3.0
spring.kafka.properties.session.timeout.ms=45000

# Required connection configs for Confluent Cloud Schema Registry
spring.kafka.properties.basic.auth.credentials.source=USER_INFO
spring.kafka.properties.basic.auth.user.info={{ SR_API_KEY }}:{{ SR_API_SECRET }}
spring.kafka.properties.schema.registry.url=https://{{ SR_ENDPOINT }}

*/