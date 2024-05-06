package org.example.service.model;

public record Url(String shortForm, String longForm) {
    public Url(String longForm) {
        this(null, longForm);
    }
}
