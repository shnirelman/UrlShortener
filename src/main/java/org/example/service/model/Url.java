package org.example.service.model;

public record Url(Long id, Long userId, String shortForm, String longForm) {

    public Url(Long id, Long userid, String longForm) {
        this(id, userid, null, longForm);
    }
    public Url(String longForm) {
        this(null, null, null, longForm);
    }
    public Url(Long id, String longForm) {
        this(id, null, null, longForm);
    }
}
