package com.example.redisapp.controller;

import com.example.redisapp.service.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/add")
public class AddDataController {
    @Autowired
    private SyncService syncService;

    @GetMapping("/addData")
    public ResponseEntity<String> addData(@RequestParam String key, @RequestParam String value) {
        syncService.addData(key, value);
        return ResponseEntity.ok("Data added successfully");
    }
}
