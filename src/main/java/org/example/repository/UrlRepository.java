package org.example.repository;

import org.example.repository.dao.UrlDao;

import java.sql.SQLException;
import java.util.Optional;

public interface UrlRepository {

    Optional<UrlDao> findUrlByLongForm(String longForm) throws SQLException;

    Optional<UrlDao> findUrlByShortForm(String shortForm) throws SQLException;
    void save(UrlDao urlDao) throws SQLException;
}
