package com.example.finalAssignment.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Forecast {
   private String date;
   private Integer temperature;
    private String weather_description;
    private Integer humidity;
    private Integer wind_speed;

}
