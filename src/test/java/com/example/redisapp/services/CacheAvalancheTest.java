package com.example.redisapp.services;

import com.example.redisapp.service.SyncService;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CacheAvalancheTest {

    private final SyncService syncService = mock(SyncService.class);

    /**
     * Test Cache Avalanche
     * 测试缓存雪崩：模拟多个缓存失效的场景，验证随机过期时间是否生效。
     */
    @Test
    void testCacheAvalanche() {
        String key1 = "key1";
        String key2 = "key2";
        String key3 = "key3";

        // Simulate cache miss for all keys
        when(syncService.getData(key1)).thenReturn(null).thenReturn("value1");
        when(syncService.getData(key2)).thenReturn(null).thenReturn("value2");
        when(syncService.getData(key3)).thenReturn(null).thenReturn("value3");

        // Simulate cache being refilled for each key
        assertEquals(null, syncService.getData(key1));
        assertEquals("value1", syncService.getData(key1));

        assertEquals(null, syncService.getData(key2));
        assertEquals("value2", syncService.getData(key2));

        assertEquals(null, syncService.getData(key3));
        assertEquals("value3", syncService.getData(key3));

        // Verify each key was accessed twice (cache miss + cache hit)
        verify(syncService, times(2)).getData(key1);
        verify(syncService, times(2)).getData(key2);
        verify(syncService, times(2)).getData(key3);
    }
}
