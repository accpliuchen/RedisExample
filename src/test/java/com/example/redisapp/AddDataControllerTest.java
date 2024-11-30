package com.example.redisapp;

import com.example.redisapp.service.CacheService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureMockMvc
public class AddDataControllerTest {

    @Autowired
    private MockMvc mockMvc;

//    @Test
//    public void testAddData() throws Exception {
//        mockMvc.perform(get("/api/add/addData")
//                        .param("key", "989")
//                        .param("value", "999"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Data added successfully"));
//    }

    @Test
    public void testAddDataToCacheWithTTL() {
        // Mock RedisTemplate and ValueOperations
        RedisTemplate<String, Object> mockRedisTemplate = Mockito.mock(RedisTemplate.class);
        ValueOperations<String, Object> mockValueOperations = Mockito.mock(ValueOperations.class);

        // Stub RedisTemplate to return the mocked ValueOperations
        when(mockRedisTemplate.opsForValue()).thenReturn(mockValueOperations);

        // Create RedisService with the mocked RedisTemplate
        CacheService redisService = new CacheService(mockRedisTemplate);

        // Test inputs
        String key = "testKey";
        String value = "testValue";

        // Call the method to test
        redisService.addDataToCache(key, value);

        // Verify that the set method was called with the correct arguments
        verify(mockValueOperations).set(eq(key), eq(value), any(Duration.class));
    }

}
