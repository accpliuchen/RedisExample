package com.example.redisapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/testdata")
public class DataController {

    @GetMapping("/addData")
    public String addData(@RequestParam String key, @RequestParam String value) {
        return "Data added: key=" + key + ", value=" + value;
    }
}
