package org.example.service;

import org.example.exception.EntityNotFoundException;
import org.example.repository.UrlRepository;
import org.example.service.model.Url;

public interface UrlService {
    String addUrl(Url url);

    Url getUrl(String shortForm) throws EntityNotFoundException;
}
