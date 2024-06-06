package org.example.service;

import org.example.exception.EntityNotFoundException;
import org.example.service.model.Url;

import java.util.List;

public interface UrlService {
    /**
     * create a new short form of url or find it if exists
     * @param url long form of url
     * @return short form of url
     */
    String addUrl(Url url);

    /**
     * find existing url by short form
     * @param shortForm
     * @return {@link Url} long form of url with id
     * @throws EntityNotFoundException if url with provided short form not found
     */
    Url getUrl(String shortForm) throws EntityNotFoundException;

    /**
     * delete url by id
     * @param id id of url
     */
    void deleteUrl(Long id);

    /**
     * find all urls that not used for a day
     * @return list of ids
     */
    List<Long> getAllNotUpdatedForADay();

    /**
     * update time of using url
     * @param id id of url
     */
    void updateUsedTime(Long id);

    boolean checkUnusedUrlBeforeDelete(Long id);
}
