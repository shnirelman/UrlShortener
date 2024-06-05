package org.example.kafka;

import org.example.kafka.dto.UrlDeleteKafkaMessage;
import org.example.service.UrlService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UrlDeleteConsumer {

    private final UrlService urlService;

    public UrlDeleteConsumer(UrlService urlService) {
        this.urlService = urlService;
    }

    @KafkaListener(topics = "url-delete-topic", containerFactory="kafkaDeleteUrlListenerContainerFactory")
    public void consume(UrlDeleteKafkaMessage message) {
        if (message.id() != null) {
            System.out.println("consume " + message.id());
            urlService.deleteUrl(message.id());
        }
    }
}
