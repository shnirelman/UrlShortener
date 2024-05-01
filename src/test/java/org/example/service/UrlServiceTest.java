package org.example.service;

import org.example.exception.EntityNotFoundException;
import org.example.repository.UrlRepositoryImpl;
import org.example.service.model.Url;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UrlServiceTest {
    private final UrlService urlService = new UrlServiceImpl(new UrlRepositoryImpl());
    
    @Test
    void testAddUrl() throws EntityNotFoundException {
        //given:
        Url url = new Url("abacaba.com");
        //when:
        String shortForm = urlService.addUrl(url);
        Url savedUrl = urlService.getUrl(shortForm);
        //then:
        assertEquals(url.longForm(), savedUrl.longForm());
    }

    @Test
    void testAddSameUrlMultipleTimes() throws EntityNotFoundException {
        //given:
        Url url = new Url("abacaba.com");
        //when:
        String shortForm = urlService.addUrl(url);
        //then:
        for(int i = 0; i < 10; i++) {
            String newShortForm = urlService.addUrl(url);
            assertEquals(shortForm, newShortForm);
        }
    }

    @Test
    void testGetMissingUrl() throws EntityNotFoundException {
        //given:
        String shortForm = "https://shorten.com/abacaba";
        //when:
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> {
            urlService.getUrl(shortForm);
        });
        //then:
        assertEquals(ex.getMessage(), "Ссылка не найдена");
    }
}
