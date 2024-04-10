package org.example.controller;

import org.example.controller.dto.UrlDto;
import org.example.exception.EntityNotFoundException;
import org.example.repository.UrlRepository;
import org.example.service.UrlService;
import org.example.service.model.Url;

public class UrlController {
    private final UrlService urlService;


    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    public String addUrl(UrlDto urlDto) {
        return urlService.addUrl(new Url(urlDto.longForm()));
    }

    public UrlDto getUrlByShortForm(String shortForm) throws EntityNotFoundException {
        Url url = urlService.getUrl(shortForm);
        return new UrlDto(url.longForm(), url.shortForm());
    }
}
