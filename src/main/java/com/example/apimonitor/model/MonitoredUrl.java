package com.example.apimonitor.model;

import lombok.Data;

@Data
public class MonitoredUrl {
    private String url;
    private int intervalInSeconds;
    private boolean up;
    private String lastError;
}
