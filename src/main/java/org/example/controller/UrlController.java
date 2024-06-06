package org.example.controller;

import org.example.controller.dto.UrlDto;
import org.example.exception.EntityNotFoundException;
import org.example.service.UrlService;
import org.example.service.UrlServiceImpl;
import org.example.service.UserService;
import org.example.service.model.Url;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/url")
public class UrlController {
    private final UrlService urlService;

    private final UserService userService;

    public UrlController(UrlService urlService, UserService userService) {
        this.urlService = urlService;
        this.userService = userService;
    }

    @PostMapping(value = "/create")
    public String addUrl(@RequestBody UrlDto urlDto) throws EntityNotFoundException {
        Long userId = userService.getId(urlDto.userName());
        System.out.println("userId = " + userId);
        return UrlServiceImpl.shortFormPrefix + urlDto.userName() + "/" + urlService.addUrl(new Url(null, userId, urlDto.longForm()));
    }

    @GetMapping("/get/{userName}/{shortFormSuffix}")
    public String getUrlByShortForm(@PathVariable("userName") String userName
            , @PathVariable("shortFormSuffix") String shortFormSuffix) throws EntityNotFoundException {
        System.out.println("getUrlByShortForm " + shortFormSuffix);
        Long userId = userService.getId(userName);
        Url url = urlService.getUrl(userId, shortFormSuffix);
        urlService.updateUsedTime(url.id());
        return UrlServiceImpl.shortFormPrefix + userName + "/" + url.longForm();
    }

    @GetMapping("/get/{userName}")
    public List<UrlDto> getUrlsBySUserName(@PathVariable("userName") String userName) throws EntityNotFoundException {
        Long userId = userService.getId(userName);
        List<Url> urls = urlService.getUrlsByUserId(userId);
        ArrayList<UrlDto> res = new ArrayList<>();
        for(Url url : urls) {
            res.add(new UrlDto(userName, url.longForm(), url.shortForm()));
        }
        return res;
    }
}
