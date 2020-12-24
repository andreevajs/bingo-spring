package com.example.bingospring.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class MainController {

    Logger logger = LoggerFactory.getLogger(MainController.class);

    @ResponseBody
    public String index() {
        logger.info("index");
        return "index";
    }
}