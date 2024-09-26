package com.example.finalAssignment.interfaces;

import com.example.finalAssignment.dtos.Weather;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "weather" , url = "https://freetestapi.com/api/v1/weathers")
public interface WeatherClient {
    @GetMapping
    public Weather[] getWeatherOfEmployee(@RequestParam("search") String search);
}
