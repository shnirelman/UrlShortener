package org.example.service.model;

public record Url(Long id, String shortForm, String longForm) {
    public Url(String longForm) {
        this(null, null, longForm);
    }
    public Url(Long id, String longForm) {
        this(id, null, longForm);
    }
}
