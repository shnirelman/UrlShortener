package org.example.job;

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.example.kafka.UrlDeleteProducer;
import org.example.service.UrlService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UrlDeleteScheduler {

    private static final String URL_DELETE_LOCK_NAME = "url-delete-lock";

    private final UrlDeleteProducer urlDeleteProducer;
    private final UrlService urlService;

    public UrlDeleteScheduler(UrlDeleteProducer urlDeleteProducer, UrlService urlService) {
        this.urlDeleteProducer = urlDeleteProducer;
        this.urlService = urlService;
    }


    /**
     * find urls that haven't been accessed for a long time and send their ids to kafka consumers so that they delete these urls
     */

    @SchedulerLock(name = URL_DELETE_LOCK_NAME, lockAtMostFor = "PT30S", lockAtLeastFor = "PT30S")
    @Scheduled(cron = "* * * * * *", zone = "Europe/Moscow")
//    @Scheduled(fixedDelay = 1000)
    public void deleteUrl() {
        System.out.println("scheduled delete");
        try {
            List<Long> ids = urlService.getAllNotUpdatedForADay();
            for(Long id : ids) {
                System.out.println("id to delete = " + id);
            }
            urlDeleteProducer.sendMessages(ids);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
