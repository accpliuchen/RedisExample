package com.example.redisapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class CacheService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * Retrieve data from Redis cache
     */
    public Object getCachedData(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * Add data to Redis cache
     */
    public void addDataToCache(String key, Object value) {
        redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(300 + (int) (Math.random() * 60))); // 防止雪崩
    }

    /**
     * Delete data from Redis cache
     */
    public void deleteCache(String key) {
        redisTemplate.delete(key);
    }
}
