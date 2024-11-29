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
     * 查询数据（防止雪崩、击穿）
     */
    public Object getCachedData(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        if (value == null) {
            synchronized (key.intern()) { // 防止击穿
                value = redisTemplate.opsForValue().get(key);
                if (value == null) {
                    return null;
                }
            }
        }
        return value;
    }

    /**
     * 添加数据到缓存
     */
    public void addDataToCache(String key, Object value) {
        redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(300 + (int) (Math.random() * 60)));
    }

    /**
     * 删除缓存
     */
    public void deleteCache(String key) {
        redisTemplate.delete(key);
    }
}
