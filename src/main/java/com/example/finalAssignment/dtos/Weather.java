package com.example.finalAssignment.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {
   private Integer id;
   private String city;
    private String country;
    private Integer latitude;
    private Integer longitude;
    private Integer temperature;
    private String weather_description;
    private Integer humidity;
    private Integer wind_speed;
    private List<Forecast> forecast;


}
