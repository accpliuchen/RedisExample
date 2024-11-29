package com.example.redisapp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Utility Class for Distributed Lock using Redis
 */
@Component
public class LockUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * Try to acquire a distributed lock
     *
     * @param lockKey    Key of the lock
     * @param requestId  Request ID to uniquely identify the lock owner
     * @param expireTime Expiration time for the lock in seconds
     * @return Whether the lock is acquired
     */
    public boolean tryLock(String lockKey, String requestId, long expireTime) {
        Boolean result = redisTemplate.opsForValue().setIfAbsent(lockKey, requestId, expireTime, TimeUnit.SECONDS);
        return result != null && result;
    }

    /**
     * Release the distributed lock
     *
     * @param lockKey   Key of the lock
     * @param requestId Request ID to validate the lock owner
     * @return Whether the lock is released
     */
    public boolean releaseLock(String lockKey, String requestId) {
        String currentValue = redisTemplate.opsForValue().get(lockKey);
        if (requestId.equals(currentValue)) {
            redisTemplate.delete(lockKey);
            return true;
        }
        return false;
    }
}
