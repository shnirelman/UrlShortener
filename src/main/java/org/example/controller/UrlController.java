package org.example.controller;

import org.example.controller.dto.UrlDto;
import org.example.exception.EntityNotFoundException;
import org.example.repository.UrlRepository;
import org.example.service.UrlService;
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

    @GetMapping("/{shortFrom}")
    public UrlDto getUrlByShortForm(@PathVariable("shortFrom") String shortForm) throws EntityNotFoundException {
        System.out.println("get url" + shortForm);
        Url url = urlService.getUrl(shortForm);
        return new UrlDto(url.longForm(), url.shortForm());
    }
}
