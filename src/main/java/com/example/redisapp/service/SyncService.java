package com.example.redisapp.service;

import com.example.redisapp.entity.DataEntity;
import com.example.redisapp.util.LockUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SyncService {

    @Autowired
    private CacheService cacheService;

    @Autowired
    private DatabaseService databaseService;

    @Autowired
    private LockUtil lockUtil;

    private static final String LOCK_KEY_PREFIX = "lock:";

    /**
     * Retrieve data with cache and database fallback, prevent cache penetration
     *
     * @param key  to retrieve
     * @return value retrieved
     */
    public Object getData(String key) {
        // Attempt to retrieve data from the cache
        Object cachedValue = cacheService.getCachedData(key);
        if (cachedValue != null) {
            // Cache hit
            return cachedValue.equals("null") ? null : cachedValue; // Return null to indicate no record in the database
        }

        String lockKey = LOCK_KEY_PREFIX + key;
        String requestId = String.valueOf(Thread.currentThread().getId());

        try {
            // Attempt to acquire the distributed lock
            if (lockUtil.tryLock(lockKey, requestId, 10)) {
                // Recheck the cache to prevent issues if another thread has updated it
                cachedValue = cacheService.getCachedData(key);
                if (cachedValue != null) {
                    return cachedValue.equals("null") ? null : cachedValue;
                }

                // Cache and database both missed, query the database
                Optional<DataEntity> entity = databaseService.getDataFromDB(key);
                if (entity.isPresent()) {
                    // Add the query result to the cache
                    cacheService.addDataToCache(key, entity.get().getValue());
                    return entity.get().getValue();
                } else {
                    // Prevent cache penetration by caching a null value
                    cacheService.addDataToCache(key, "null");
                    return null;
                }
            } else {
                // If the lock is not acquired, wait for a moment and retry
                Thread.sleep(50);
                return getData(key); // Retry recursively
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        } finally {
            // Release the lock
            lockUtil.releaseLock(lockKey, requestId);
        }
    }

    /**
     * Add data to both database and cache
     */
    public void addData(String key, String value) {
        databaseService.saveData(key, value);
        cacheService.addDataToCache(key, value);
    }

    /**
     * 删除数据
     * Delete data from both database and cache
     */
    public void deleteData(String key) {
        databaseService.deleteData(key);
        cacheService.deleteCache(key);
    }
}