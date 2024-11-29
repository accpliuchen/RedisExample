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
     * 查询数据，优先从缓存中读取
     */
    public Object getData(String key) {
        Object value = cacheService.getCachedData(key);
        if (value == null) {
            Optional<DataEntity> entity = databaseService.getDataFromDB(key);
            if (entity.isPresent()) {
                cacheService.addDataToCache(key, entity.get().getValue());
                return entity.get().getValue();
            }
            return null;
        }
        return value;
    }

    /**
     * 添加数据到数据库并同步到缓存
     */
    public void addData(String key, String value) {
        databaseService.saveData(key, value);
        cacheService.addDataToCache(key, value);
    }

    /**
     * 删除数据库和缓存中的数据
     */
    public void deleteData(String key) {
        databaseService.deleteData(key);
        cacheService.deleteCache(key);
    }
}
