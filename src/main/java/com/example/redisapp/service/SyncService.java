package com.example.redisapp.service;

import com.example.redisapp.entity.DataEntity;
import com.example.redisapp.util.BloomFilterUtil;
import com.example.redisapp.util.LockUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SyncService {

    @Autowired
    private CacheService cacheService;

    @Autowired
    private DatabaseService databaseService;

    @Autowired
    private BloomFilterUtil bloomFilterUtil;

    @Autowired
    private LockUtil lockUtil;

    /**
     * Get data with cache fallback, Bloom Filter, and Locking.
     */
    public Object getData(String key) {
        // Step 1: Check Bloom Filter
        if (!bloomFilterUtil.mightContain(key)) {
            return null; // Key
        }

        // Step 2: Attempt to get from cache
        Object value = cacheService.getDataFromCache(key);
        if (value == null) {
            String lockKey = "lock:" + key;
            String requestId = UUID.randomUUID().toString();

            try {
                // Step 3: Try acquiring lock
                if (lockUtil.tryLock(lockKey, requestId, 5)) {
                    value = cacheService.getDataFromCache(key);
                    if (value == null) {
                        // Fetch from database and update cache
                        Optional<DataEntity> entity = databaseService.getDataFromDB(key);
                        if (entity.isPresent()) {
                            value = entity.get().getValue();
                            cacheService.addDataToCache(key, value);
                        } else {
                            // Cache null value to prevent penetration
                            cacheService.addDataToCache(key, "null");
                        }
                    }
                } else {
                    Thread.sleep(100); // Retry after a short delay
                    return getData(key);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            } finally {
                lockUtil.releaseLock(lockKey, requestId);
            }
        }
        return "null".equals(value) ? null : value;
    }

    /**
     * Add data to database and cache.
     */
    public void addData(String key, String value) {
        databaseService.saveData(key, value);
        cacheService.addDataToCache(key, value);
        bloomFilterUtil.add(key);
    }

    /**
     * Delete data from database and cache.
     */
    public void deleteData(String key) {
        databaseService.deleteData(key);
        cacheService.deleteCache(key);
    }
}