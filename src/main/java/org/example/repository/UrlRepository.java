package org.example.repository;

import org.example.repository.dao.UrlDao;

import java.util.Optional;

public interface UrlRepository {

    Optional<UrlDao> findUrlByLongForm(String longForm);

    Optional<UrlDao> findUrlByShortForm(String shortForm);
    void save(UrlDao urlDao);
}
