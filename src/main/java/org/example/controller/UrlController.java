package org.example.controller;

import org.example.controller.dto.UrlDto;
import org.example.exception.EntityNotFoundException;
import org.example.repository.UrlRepository;
import org.example.service.UrlService;
import org.example.service.UrlServiceImpl;
import org.example.service.model.Url;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/url")
public class UrlController {
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping(value = "/create")
    public String addUrl(@RequestBody UrlDto urlDto) {
        return urlService.addUrl(new Url(urlDto.longForm()));
    }

    @GetMapping("/{shortFormSuffix}")
    public String getUrlByShortForm(@PathVariable("shortFormSuffix") String shortFormSuffix) throws EntityNotFoundException {
        Url url = urlService.getUrl(UrlServiceImpl.shortFormPrefix + shortFormSuffix);
        return url.longForm();
    }
}
