package com.example.bingospring.controllers;

import jdk.jfr.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BingoCellsControllerTest {
    @Autowired
    MockMvc mockMvc;


    @Test
    public void get_2xx_cellExists() throws Exception {
        mockMvc.perform(get("/bingocell?id=1"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void get_4xx_invalidId() throws Exception {
        mockMvc.perform(get("/bingocell?id=1000000000000"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void post_4xx_emptyBody() throws Exception {
        mockMvc.perform(post("/bingocell?bingo=1"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void post_4xx_invalidModel() throws Exception {
        mockMvc.perform(post("/bingocell?bingo=1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"fail\": \"yes\"}"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void post_403_notAuth() throws Exception {
        mockMvc.perform(post("/bingocell?bingo=1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"content\": \"test\", \"row\": 1, \"column\": 3,\"id\": null}"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
