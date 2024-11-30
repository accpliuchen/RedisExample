package com.example.redisapp;

import com.example.redisapp.service.SyncService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class CacheControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetData() throws Exception {
        // Test valid key
        mockMvc.perform(get("/api/cache/getDataCache/989"))
                .andExpect(status().isOk())
                .andExpect(content().string("hotValue"));

//        // Test missing key
//        mockMvc.perform(get("/api/cache/keyX"))
//                .andExpect(status().isNotFound());
    }
}
