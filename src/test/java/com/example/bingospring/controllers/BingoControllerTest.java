package com.example.bingospring.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BingoControllerTest {
    @Autowired
    MockMvc mockMvc;


    @Test
    public void get_2xx() throws Exception {
        mockMvc.perform(get("/bingo"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void get_2xx_json() throws Exception {
        mockMvc.perform(get("/bingo"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void get_2xx_withDbData() throws Exception {
        mockMvc.perform(get("/bingo"))
                .andDo(print())
                .andExpect(content().string(any(String.class)))
                .andExpect(content().string(containsString("bingo")))
                .andExpect(content().string(containsString("\"id\":")));
    }

    @Test
    public void get_2xx_returnsById() throws Exception {
        mockMvc.perform(get("/bingo/1"))
                .andDo(print())
                .andExpect(content().string(any(String.class)))
                .andExpect(content().string(containsString("Traveller")));
    }

    @Test
    public void get_2xx_returnsCells() throws Exception {
        mockMvc.perform(get("/bingo/1"))
                .andDo(print())
                .andExpect(content().string(containsString("climb the mountain")));
    }

    @Test
    public void get_4xx_invalidId() throws Exception {
        mockMvc.perform(get("/bingo/0"))
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
    public void post_403_notAuth() throws Exception {
        mockMvc.perform(post("/bingo")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"content\": \"test\", \"row\": 1, \"column\": 3,\"id\": null}"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void put_403_notAuth() throws Exception {
        mockMvc.perform(put("/bingo")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"content\": \"test\", \"row\": 1, \"column\": 3,\"id\": null}"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
