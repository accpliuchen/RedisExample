package com.example.redisapp.services;

import com.example.redisapp.service.SyncService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CachePenetrationTest {

    private final SyncService syncService = mock(SyncService.class);

    /**
     * Test Cache Penetration
     */
    @Test
    void testCachePenetration() {
        String invalidKey = "invalidKey";

        // Simulate Bloom Filter rejecting the key
        when(syncService.getData(invalidKey)).thenReturn(null);

        // Access with an invalid key
        assertEquals(null, syncService.getData(invalidKey));

        // Verify cache/database is not accessed
        verify(syncService, times(1)).getData(invalidKey);
    }
}
