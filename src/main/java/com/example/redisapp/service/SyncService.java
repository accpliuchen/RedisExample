package com.example.redisapp.service;

import com.example.redisapp.entity.DataEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SyncService {

    @Autowired
    private CacheService cacheService;

    @Autowired
    private DatabaseService databaseService;

    /**
     * Retrieve data from cache first, fallback to database if not found
     *
     * @param key  Key to retrieve
     * @return Value retrieved
     */
    public Object getData(String key) {
        Object cachedValue = cacheService.getCachedData(key);
        if (cachedValue != null) {
            return cachedValue;
        }

        Optional<DataEntity> entity = databaseService.getDataFromDB(key);
        if (entity.isPresent()) {
            cacheService.addDataToCache(key, entity.get().getValue());
            return entity.get().getValue();
        }

        return null;
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