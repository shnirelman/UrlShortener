package org.example.controller.dto;

public record UrlDto(String userName, String longForm, String shortForm) {
    public UrlDto(String longForm) {
        this(null, longForm, null);
    }
    public UrlDto(String userName, String longForm) {
        this(userName, longForm, null);
    }
}
