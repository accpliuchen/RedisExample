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
     * 获取数据
     * Retrieve data with cache and database fallback
     */
    public Object getData(String key) {
        Object value = cacheService.getCachedData(key);
        if (value == null) { // 缓存未命中
            return databaseService.getDataFromDB(key).orElse(null);
        }
        return value;
    }

    /**
     * 添加数据
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