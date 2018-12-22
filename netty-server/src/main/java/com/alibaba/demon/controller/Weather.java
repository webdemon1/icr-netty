package com.alibaba.demon.controller;

import com.alibaba.demon.service.WeatherService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/weather")
public class Weather {

    @Resource
    private WeatherService weatherService;

    @GetMapping(("/getCurrentWeather"))
    public JSONObject getCurrentWeather() {
        return weatherService.getCurrentWeather();
    }
}
