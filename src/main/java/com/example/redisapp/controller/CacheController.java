package com.example.redisapp.controller;

import com.example.redisapp.service.CacheService;
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


    @Autowired
    private CacheService cacheService;

//    @GetMapping("/{key}")
//    public ResponseEntity<Object> getData(@PathVariable String key) {
//        try {
//            Object value = syncService.getData(key);
//            if (value != null) {
//                return ResponseEntity.ok(value);
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data not found");
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }

    @GetMapping("/getDataCache/{key}")
    public ResponseEntity<Object> getDataCache(@PathVariable String key) {
        // Step 1: Attempt to get data from cache
        Object cachedValue = cacheService.getDataFromCache(key);
        if (cachedValue != null) {
            return ResponseEntity.ok(cachedValue);
        }

        // Step 2: Fetch from SyncService (with locking and caching)
        Object value = syncService.getData(key);
        if (value != null) {
            cacheService.addDataToCache(key, value); // 更新缓存
            return ResponseEntity.ok(value);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data not found");
    }

    /**
     * Retrieve data with cache fallback and handling for penetration, breakdown, and avalanche scenarios.
     *
     * @param key The key to retrieve the data
     * @return The response entity containing the value or an error message
     */
    @GetMapping("/{key}")
    public ResponseEntity<Object> getData(@PathVariable String key) {
        // Step 1: Prevent cache penetration using Bloom filter
        if (!syncService.isKeyValid(key)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Key is invalid");
        }

        // Step 2: Retrieve data from cache, handling breakdown and avalanche scenarios
        Object value = syncService.getData(key);
        if (value != null) {
            return ResponseEntity.ok(value);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data not found");
    }
}
