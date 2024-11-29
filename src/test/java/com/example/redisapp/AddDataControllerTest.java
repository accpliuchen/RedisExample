package com.example.redisapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class AddDataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAddData() throws Exception {
        mockMvc.perform(get("/api/add/addData")
                        .param("key", "888")
                        .param("value", "999"))
                .andExpect(status().isOk())
                .andExpect(content().string("Data added successfully"));
    }
}
