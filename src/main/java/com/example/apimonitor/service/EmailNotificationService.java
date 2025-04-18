package com.example.apimonitor.service;

import com.example.apimonitor.model.MonitoredUrl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService implements NotificationService {

    private final JavaMailSender mailSender;

    @Value("${monitor.alert.email}")
    private String toEmail;

    public EmailNotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendDown(MonitoredUrl url, String error) {
        send(url.getUrl() + "упал((((", error);
    }

    @Override
    public void sendBackOnline(MonitoredUrl url) {
        send(url.getUrl() + "поднялся)))))", "воркает");
    }

    private void send(String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}

