package org.example.service;

import org.example.exception.EntityNotFoundException;
import org.example.repository.UrlRepository;
import org.example.repository.entity.UrlEntity;
import org.example.repository.entity.UserEntity;
import org.example.service.model.Url;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UrlServiceImpl implements UrlService {
    public static final String shortFormPrefix = "http://localhost:8080/url/get/";
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
        res = urlRepository.findByUserIdAndLongForm(url.userId(), url.longForm());
        if(res.isPresent())
            return shortFormPrefix + res.get().getShortForm();

        long hash = primaryHash(url.longForm());
        String shortForm = null;
        boolean exists = true;
        while(exists) {
            shortForm = longToShortForm(hash);
            hash = (hash == MOD - 1 ? 0L : hash + 1);

            exists = urlRepository.findByUserIdAndShortForm(url.userId(), shortForm).isPresent();
        }

        urlRepository.save(new UrlEntity(url.userId(), shortForm, url.longForm(), Instant.now()));

        return shortForm;
    }

    @Override
    @Cacheable(cacheNames = "urls", cacheManager = "cacheManager")
    public Url getUrl(Long userId, String shortForm) throws EntityNotFoundException {
        Optional<UrlEntity> urlEntity = null;
        urlEntity = urlRepository.findByUserIdAndShortForm(userId, shortForm);

        if(urlEntity.isEmpty() || !Objects.equals(urlEntity.get().getUserId(), userId))
            throw new EntityNotFoundException("Короткая ссылка " + shortForm + " ничему не соответствует.");

        UrlEntity url = urlEntity.get();
        return new Url(url.getId(), url.getUserId(), url.getShortForm(), url.getLongForm());
    }

    @Override
    public void updateUsedTime(Long id) {
        Thread thread = new Thread(() -> {
            urlRepository.updateUsedTime(id, Instant.now());
        });
        thread.start();
    }

    public void updateUsedTime(List<Long> ids) {
        Thread thread = new Thread(() -> {
            for(Long id : ids)
                urlRepository.updateUsedTime(id, Instant.now());
        });
        thread.start();
    }

    @Override
    public boolean checkUnusedUrlBeforeDelete(Long id) {
        Optional<UrlEntity> url = urlRepository.findById(id);
        return url.isPresent() && url.get().getUsedAt().compareTo(getTimeToDelete()) <= 0;
    }

    @Override
    public List<Url> getUrlsByUserId(Long userId) {
        List<UrlEntity> urls = urlRepository.findByUserId(userId);
        ArrayList<Url> res = new ArrayList<>();
        ArrayList<Long> ids = new ArrayList<>();
        for(UrlEntity urlEntity : urls) {
            res.add(new Url(urlEntity.getId(), urlEntity.getUserId(), urlEntity.getShortForm(), urlEntity.getLongForm()));
            ids.add(urlEntity.getId());
        }
        updateUsedTime(ids);
        return res;
    }

    @Override
    public void deleteUrl(Long id) {
        urlRepository.deleteById(id);
    }

    @Override
    public List<Long> getAllNotUpdatedForADay() {
        return urlRepository.findIdsByUsedAtLessThan(getTimeToDelete());
    }

    private Instant getTimeToDelete() {
        return Instant.now().minus(1, ChronoUnit.MINUTES);
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
