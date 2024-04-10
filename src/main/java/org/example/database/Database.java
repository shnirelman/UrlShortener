package org.example.database;

import org.example.repository.dao.UrlDao;

import java.util.HashMap;
import java.util.Map;

public class Database {
    private static Database instance;

    private final Map<String, UrlDao> urlsMapFromShort;
    private final Map<String, UrlDao> urlsMapFromLong;

    private Database() {
        urlsMapFromShort = new HashMap<>();
        urlsMapFromLong = new HashMap<>();
    }

    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public void saveUrl(UrlDao urlDao) {
        getInstance().urlsMapFromShort.put(urlDao.shortForm(), urlDao);
        getInstance().urlsMapFromLong.put(urlDao.longForm(), urlDao);
    }

    public UrlDao getUrlByLongForm(String longForm) {
        return getInstance().urlsMapFromLong.get(longForm);
    }

    public UrlDao getUrlByShortForm(String shortForm) {
        return getInstance().urlsMapFromShort.get(shortForm);
    }

}
