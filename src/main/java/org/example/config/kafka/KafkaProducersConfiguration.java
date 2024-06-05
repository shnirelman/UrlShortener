package org.example.config.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.example.kafka.dto.UrlDeleteKafkaMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.JacksonUtils;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducersConfiguration {


    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ObjectMapper objectMapper() {
        return JacksonUtils.enhancedObjectMapper();
    }
    @Bean
    public ProducerFactory<String, UrlDeleteKafkaMessage> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        System.out.println("producerFactory  bootstrapServers = " + bootstrapServers);
        //var configProps = kafkaProperties.buildProducerProperties();
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers);
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);
                //new JsonSerializer<UrlDeleteKafkaMessage>());
        return new DefaultKafkaProducerFactory<String, UrlDeleteKafkaMessage>(configProps);
//        var kafkaProducerFactory = new DefaultKafkaProducerFactory<String, UrlDeleteKafkaMessage>(configProps);
//        kafkaProducerFactory.setValueSerializer(new JsonSerializer<>(mapper));
//        return kafkaProducerFactory;
    }

    @Bean
    public KafkaTemplate<String, UrlDeleteKafkaMessage> kafkaUrlDeleteTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public NewTopic newTopic() {
        return new NewTopic("url-delete-topic", 1, (short)1);
    }
}
