package org.example.controller;

import org.example.kafka.UrlDeleteProducer;
import org.example.repository.UrlRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class KafkaTestController {
    private final UrlDeleteProducer urlDeleteProducer;
    private final UrlRepository urlRepository;

    public KafkaTestController(UrlDeleteProducer urlDeleteProducer, UrlRepository urlRepository) {
        this.urlDeleteProducer = urlDeleteProducer;
        this.urlRepository = urlRepository;
    }

    @GetMapping("/kafkatest")
    public String kafkaTest() {
        System.out.println("kafkaTest");
        List<Long> ids = new ArrayList<>();
        ids.add(25L);
        urlDeleteProducer.sendMessages(ids);
        return "ok";
    }

    @GetMapping("/repotest")
    public String repoTest() {
        System.out.println("repoTest");
        List<Long> ids = urlRepository.findAllTest(1L);
        for(Long id : ids) {
            System.out.println("repoTest has found: " + id);
        }
        return "ok";
    }
}
