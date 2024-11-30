package com.example.redisapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * Service for Redis Cache Operations
 */
@Service
public class CacheService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    public CacheService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * Retrieve data from Redis cache
     *
     * @param key  Key to retrieve
     * @return  Value retrieved
     */
    public Object getCachedData(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * Add data to Redis cache
     *
     * @param key   Key to add
     * @param value Value to add
     */
    public void addDataToCache(String key, Object value) {
        // Set a random expiration time for the cache to prevent cache avalanche.
        // The expiration time is set to 300 seconds plus a random value between 0 and 60 seconds.
        // By adding a random expiration offset, it reduces the likelihood of multiple keys expiring at the same time,
        // which could lead to a sudden spike in database requests (cache avalanche).
        redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(300 + (int) (Math.random() * 60)));
    }

    /**
     * Delete data from Redis cache
     *
     * @param key  Key to delete
     */
    public void deleteCache(String key) {
        redisTemplate.delete(key);
    }
}
