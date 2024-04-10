package org.example.service;

import org.example.exception.EntityNotFoundException;
import org.example.repository.UrlRepository;
import org.example.repository.dao.UrlDao;
import org.example.service.model.Url;

import java.util.Optional;

public class UrlServiceImpl implements UrlService {
    private final String shortFormPrefix = "https://shorten.com/";
    private final UrlRepository urlRepository;
    private final int alphabet = 62;
    private final long MOD = 1000000006189L; // random big prime number
    private final int hashp = 997; // must be odd and greater than alphabet


    public UrlServiceImpl(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Override
    public String addUrl(Url url) {
        Optional<UrlDao> res = urlRepository.findUrlByLongForm(url.longForm());
        if(res.isPresent())
            return shortFormPrefix + res.get().shortForm();

        long hash = primaryHash(url.longForm());
        String shortForm;
        do {
            shortForm = longToShortForm(hash);
            hash = (hash == MOD - 1 ? 0L : hash + 1);
        } while(urlRepository.findUrlByShortForm(shortForm).isPresent());
        urlRepository.save(new UrlDao(url.longForm(), shortForm));
        return shortFormPrefix + shortForm;
    }

    @Override
    public Url getUrl(String shortForm) throws EntityNotFoundException {
        if(shortForm.length() < shortFormPrefix.length())
            throw new EntityNotFoundException("Ссылка должна начинаться с " + shortFormPrefix);

        String prefix = shortForm.substring(0, shortFormPrefix.length());
        if(!prefix.equals(shortFormPrefix))
            throw new EntityNotFoundException("Ссылка должна начинаться с " + shortFormPrefix);

        Optional<UrlDao> urlDao = urlRepository.findUrlByShortForm(shortForm.substring(shortFormPrefix.length()));
        if(urlDao.isEmpty())
            throw new EntityNotFoundException("Ссылка не найдена");
        return new Url(urlDao.get().longForm(), urlDao.get().shortForm());
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
