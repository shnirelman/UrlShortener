package org.example.service;

import org.example.exception.EntityNotFoundException;
import org.example.repository.UrlRepository;
import org.example.repository.entity.UrlEntity;
import org.example.service.model.Url;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class UrlServiceImpl implements UrlService {
    public static final String shortFormPrefix = "http://localhost:8080/url/";
    private final UrlRepository urlRepository;
    private final int alphabet = 62;
    private final long MOD = 1000000006189L; // random big prime number
    private final int hashp = 997; // must be odd and greater than alphabet


    public UrlServiceImpl(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Override
    public String addUrl(Url url) {
        Optional<UrlEntity> res = null;
        res = urlRepository.findByLongForm(url.longForm());
        if(res.isPresent())
            return shortFormPrefix + res.get().getShortForm();

        long hash = primaryHash(url.longForm());
        String shortForm = null;
        boolean exists = true;
        while(exists) {
            shortForm = longToShortForm(hash);
            hash = (hash == MOD - 1 ? 0L : hash + 1);

            exists = urlRepository.findByShortForm(shortForm).isPresent();
        }

        urlRepository.save(new UrlEntity(shortForm, url.longForm()));

        return shortFormPrefix + shortForm;
    }

    @Override
    @Cacheable(cacheNames = "urls", cacheManager = "cacheManager")
    public Url getUrl(String shortForm) throws EntityNotFoundException {
        if(shortForm.length() < shortFormPrefix.length())
            throw new EntityNotFoundException("Ссылка должна начинаться с " + shortFormPrefix);

        String prefix = shortForm.substring(0, shortFormPrefix.length());
        if(!prefix.equals(shortFormPrefix))
            throw new EntityNotFoundException("Ссылка должна начинаться с " + shortFormPrefix);

        Optional<UrlEntity> urlEntity = null;
        urlEntity = urlRepository.findByShortForm(shortForm.substring(shortFormPrefix.length()));

        if(urlEntity.isEmpty())
            throw new EntityNotFoundException("Короткая ссылка " + shortForm + " ничему не соответствует.");

        return new Url(urlEntity.get().getShortForm(), urlEntity.get().getLongForm());
    }

    private long primaryHash(String longForm) {
        long res = 0;
        for(int i = 0; i < longForm.length(); i++) {
            res = (res * hashp + longForm.charAt(i) + 1) % MOD;
        }
        return res;
    }

    private char intToUrlSymbol(int x) {
        if(x < 10)
            return (char)(x + '0');
        else if(x < 36)
            return (char)(x - 10 + 'a');
        else
            return (char)(x - 36 + 'A');
    }

    private String longToShortForm(long x) {
        StringBuilder sb = new StringBuilder();
        x++;
        while(x > 0) {
            sb.append(intToUrlSymbol((int)(x % alphabet)));
            x /= alphabet;
        }
        return sb.toString();
    }
}
