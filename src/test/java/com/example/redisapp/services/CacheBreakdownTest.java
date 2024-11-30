package com.example.redisapp.services;

import com.example.redisapp.entity.DataEntity;
import com.example.redisapp.service.CacheService;
import com.example.redisapp.service.SyncService;
import com.example.redisapp.service.DatabaseService;
import com.example.redisapp.util.BloomFilterUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@SpringBootTest
@AutoConfigureMockMvc
public class CacheBreakdownTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SyncService syncService;

    @MockBean
    private CacheService cacheService;

    @MockBean
    private DatabaseService databaseService;

    @Test
    public void testGetData() throws Exception {

        Mockito.when(syncService.isKeyValid("989")).thenReturn(true);

        // Mock service response
        Mockito.when(syncService.getData("989")).thenReturn("999");
        Mockito.when(syncService.getData("keyX")).thenReturn(null);

        // Test valid key
        mockMvc.perform(get("/api/cache/989"))
                .andExpect(status().isOk())
                .andExpect(content().string("999"));

        // Test missing key
        mockMvc.perform(get("/api/cache/keyX"))
                .andExpect(status().isNotFound());

        // Verify invocations
        Mockito.verify(syncService, Mockito.times(1)).getData("989");
        //Mockito.verify(syncService, Mockito.times(1)).getData("keyX");
    }
}
