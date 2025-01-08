package org.example.service.model;

public record User(Long id, String name) {
    public User(String name) { this(null, name); }
}
