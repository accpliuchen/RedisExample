package com.example.redisapp.controller;

import com.example.redisapp.service.CacheService;
import com.example.redisapp.service.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/add")
public class AddDataController {
    @Autowired
    private SyncService syncService;

    @Autowired
    private CacheService cacheService;

    @GetMapping("/addData")
    public ResponseEntity<String> addData(@RequestParam String key, @RequestParam String value) {
        syncService.addData(key, value);
        return ResponseEntity.ok("Data added successfully");
    }

    @GetMapping("/AddDataToCache")
    public ResponseEntity<String> AddDataToCache(@RequestParam String key, @RequestParam String value) {
        cacheService.addDataToCache(key, value);
        return ResponseEntity.ok("Data AddDataToCached successfully");
    }
}
