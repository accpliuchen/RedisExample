package com.example.redisapp.controller;

import com.example.redisapp.service.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cache")
public class CacheController {

    @Autowired
    private SyncService syncService;

    @GetMapping("/{key}")
    public ResponseEntity<Object> getData(@PathVariable String key) {
        Object value = syncService.getData(key);
        if (value == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data not found");
        }
        return ResponseEntity.ok(value);
    }

}
