package org.example.controller.dto;

public record UrlDto(String longForm, String shortForm) {
    public UrlDto(String longForm) {
        this(longForm, null);
    }
}
