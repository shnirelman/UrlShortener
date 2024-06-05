package org.example.service;

import org.example.exception.EntityNotFoundException;
import org.example.repository.UrlRepository;
import org.example.service.model.Url;

import java.util.List;

public interface UrlService {
    String addUrl(Url url);

    Url getUrl(String shortForm) throws EntityNotFoundException;

    void deleteUrl(Long id);

    List<Long> getAllNotUpdatedForADay();

    void updateUsedTime(Long id);
}
