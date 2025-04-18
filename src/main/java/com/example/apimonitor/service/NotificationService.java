package com.example.apimonitor.service;

import com.example.apimonitor.model.MonitoredUrl;

public interface NotificationService {
    void sendDown(MonitoredUrl url, String error);
    void sendBackOnline(MonitoredUrl url);
}
