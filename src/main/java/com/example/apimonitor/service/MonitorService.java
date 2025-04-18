package com.example.apimonitor.service;

import com.example.apimonitor.model.MonitoredUrl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MonitorService {

    private final Map<String, MonitoredUrl> urls = new ConcurrentHashMap<>();

    public void addUrl(MonitoredUrl url) {
        urls.put(url.getUrl(), url);
    }

    public List<MonitoredUrl> getAll() {
        return new ArrayList<>(urls.values());
    }

    public Collection<MonitoredUrl> getUrls() {
        return urls.values();
    }
}
