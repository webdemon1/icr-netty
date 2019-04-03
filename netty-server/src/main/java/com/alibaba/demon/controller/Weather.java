package com.alibaba.demon.controller;

import com.alibaba.demon.service.WeatherService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/weather")
@Api(tags = "天气服务", description = "提供天气相关接口")
public class Weather {

    @Resource
    private WeatherService weatherService;

    @ApiOperation("天气接口")
    @GetMapping(("/getCurrentWeather"))
    public JSONObject getCurrentWeather() {
        return weatherService.getCurrentWeather();
    }
}
