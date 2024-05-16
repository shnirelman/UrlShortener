package org.example.repository.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "urls")
public class UrlEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @GeneratedValue(generator = "urls_id_seq")
//    @SequenceGenerator(name = "urls_id_seq", sequenceName = "urls_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "short_form")
    private String shortForm;

    @Column(name = "long_form")
    private String longForm;

    public UrlEntity() {

    }

    public UrlEntity(String shortForm, String longForm) {
        this.shortForm = shortForm;
        this.longForm = longForm;
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
}
