package org.example.repository.dao;

public record UrlDao(String longForm, String shortForm) {
    public UrlDao(String longForm) {
        this(longForm, null);
    }
}
