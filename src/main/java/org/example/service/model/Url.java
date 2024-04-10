package org.example.service.model;

public record Url(String longForm, String shortForm) {
    public Url(String longForm) {
        this(longForm, null);
    }
}
