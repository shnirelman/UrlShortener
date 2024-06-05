package org.example.kafka;

import org.example.kafka.dto.UrlDeleteKafkaMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UrlDeleteProducer {

    private final KafkaTemplate<String, UrlDeleteKafkaMessage> kafkaUrlDeleteTemplate;

    @Autowired
    public UrlDeleteProducer(KafkaTemplate<String, UrlDeleteKafkaMessage> kafkaUrlDeleteTemplate) {
        System.out.println("UrlDeleteProducer constructor ");
        System.out.println("UrlDeleteProducer constructor " + kafkaUrlDeleteTemplate.toString());
        this.kafkaUrlDeleteTemplate = kafkaUrlDeleteTemplate;
    }

    public void sendMessages(List<Long> ids) {
        System.out.println("UrlDeleteProducer sendMessages");
        for (Long id : ids) {
            kafkaUrlDeleteTemplate.send("url-delete-topic", new UrlDeleteKafkaMessage(id));
        }
    }


}
