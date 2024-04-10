package org.example.repository;

import org.example.database.Database;
import org.example.repository.dao.UrlDao;

import java.util.Optional;

public class UrlRepositoryImpl implements UrlRepository {

    private static final Database dataBase = Database.getInstance();

    @Override
    public Optional<UrlDao> findUrlByLongForm(String longForm) {
        return Optional.ofNullable(dataBase.getUrlByLongForm(longForm));
    }

    @Override
    public Optional<UrlDao> findUrlByShortForm(String shortForm) {
        return Optional.ofNullable(dataBase.getUrlByShortForm(shortForm));
    }

    @Override
    public void save(UrlDao urlDao) {
        dataBase.saveUrl(urlDao);
    }
}
