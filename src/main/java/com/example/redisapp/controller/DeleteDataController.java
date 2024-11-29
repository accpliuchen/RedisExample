package com.example.redisapp.controller;

import com.example.redisapp.service.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for Deleting Data
 */
@RestController
@RequestMapping("/api/delete")
public class DeleteDataController {

    @Autowired
    private SyncService syncService;

    /**
     * Delete data by key
     */
    @DeleteMapping("/{key}")
    public ResponseEntity<String> deleteData(@PathVariable String key) {
        syncService.deleteData(key);
        return ResponseEntity.ok("Data deleted successfully");
    }
}
