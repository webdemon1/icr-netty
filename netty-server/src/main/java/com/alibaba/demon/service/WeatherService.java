package com.alibaba.demon.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.squareup.okhttp.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service("weatherService")
@Slf4j
public class WeatherService {

    @Value("${weather.url}")
    private String url;

    public JSONObject getCurrentWeather() {
        Request request = new Request.Builder().url(url).build();
        OkHttpClient client = new OkHttpClient();
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful() || Objects.isNull(response.body())) {
                log.error("Call http fail");
                return new JSONObject();
            }
            ResponseBody body = response.body();
            String s = body.string();
            JSONObject jsonObject = JSON.parseObject(s, JSONObject.class);
            return Objects.isNull(jsonObject) ? new JSONObject() : jsonObject;
        }
        catch (Exception ex) {
            return new JSONObject();
        }
    }
}
