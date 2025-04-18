package com.example.apimonitor.controller;

import com.example.apimonitor.model.MonitoredUrl;
import com.example.apimonitor.service.MonitorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/urls")
public class MonitorController {

    private final MonitorService monitorService;

    public MonitorController(MonitorService monitorService) {
        this.monitorService = monitorService;
    }

    @PostMapping
    public ResponseEntity<Void> addUrl(@RequestBody MonitoredUrl url) {
        monitorService.addUrl(url);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<MonitoredUrl> getAll() {
        return monitorService.getAll();
    }
}
