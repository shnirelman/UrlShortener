package org.example.repository.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "urls")
public class UrlEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "short_form")
    private String shortForm;

    @Column(name = "long_form")
    private String longForm;

    @Column(name = "used_at")
    private Instant usedAt;

    public UrlEntity() {

    }

    public UrlEntity(String shortForm, String longForm, Instant createdAt) {
        this.shortForm = shortForm;
        this.longForm = longForm;
        this.usedAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getShortForm() {
        return shortForm;
    }

    public String getLongForm() {
        return longForm;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setShortForm(String shortForm) {
        this.shortForm = shortForm;
    }

    public void setLongForm(String longForm) {
        this.longForm = longForm;
    }

    public Instant getUsedAt() {
        return usedAt;
    }

    public void setUsedAt(Instant usedAt) {
        this.usedAt = usedAt;
    }
}
