package com.example.bingospring.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void post_4xx_usernameExists() throws Exception {
        mockMvc.perform(post("/register")
                .content("{\"username\": \"John\",\"email\": \"tester@test.test\", \"password\": \"test\", \"passwordConfirm\": \"test\"}"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void post_4xx_emailExists() throws Exception {
        mockMvc.perform(post("/register")
                .content("{\"username\": \"Bits\",\"email\": \"JNSmith@gmail.com\", \"password\": \"test\", \"passwordConfirm\": \"test\"}"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}