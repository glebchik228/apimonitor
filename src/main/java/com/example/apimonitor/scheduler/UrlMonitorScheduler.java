package com.example.apimonitor.scheduler;

import com.example.apimonitor.model.MonitoredUrl;
import com.example.apimonitor.service.MonitorService;
import com.example.apimonitor.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UrlMonitorScheduler {

    private final MonitorService monitorService;
    private final RestTemplate restTemplate;
    private final NotificationService notificationService;

    public UrlMonitorScheduler(MonitorService monitorService, NotificationService notificationService) {
        this.monitorService = monitorService;
        this.restTemplate = new RestTemplate();
        this.notificationService = notificationService;
    }

    @Scheduled(fixedDelay = 5000)
    public void checkUrls() {
        for (MonitoredUrl url : monitorService.getUrls()) {
            try {
                ResponseEntity<String> response = restTemplate.getForEntity(url.getUrl(), String.class);
                if (response.getStatusCode().is2xxSuccessful()) {
                    if (!url.isUp()) {
                        notificationService.sendBackOnline(url);
                        url.setUp(true);
                    }
                } else {
                    handleDown(url, "Non-200 response");
                }
            } catch (Exception e) {
                handleDown(url, e.getMessage());
            }
        }
    }

    private void handleDown(MonitoredUrl url, String error) {
        if (url.isUp()) {
            notificationService.sendDown(url, error);
            url.setUp(false);
            url.setLastError(error);
        }
    }
}