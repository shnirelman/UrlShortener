package org.example.kafka.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UrlDeleteKafkaMessage(
        @JsonProperty("id")
        Long id
) {
}
