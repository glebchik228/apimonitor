package com.example.apimonitor.service;

import com.example.apimonitor.model.MonitoredUrl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class TelegramNotificationService implements NotificationService {
    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.user.chat.id}")
    private String chatId;
    private final RestTemplate restTemplate = new RestTemplate();

    private void sendMessage(String message) {
        String url = "https://api.telegram.org/bot" + botToken + "/sendMessage";
        Map<String, String> params = Map.of(
                "chat_id", chatId,
                "text", message
        );
        restTemplate.postForObject(url, params, String.class);
    }

    @Override
    public void sendDown(MonitoredUrl url, String error) {
        sendMessage(url.getUrl() + "упал((((\n" + error);
    }

    @Override
    public void sendBackOnline(MonitoredUrl url) {
        sendMessage(url.getUrl() + "поднялся)))");
    }
}
