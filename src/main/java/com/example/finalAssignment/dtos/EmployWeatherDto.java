package com.example.finalAssignment.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployWeatherDto {
    private Integer Id;
    private String name;
    private String Email;
    private String Phone;
    private String Location;
    private String photoFile;
    private Weather weather;
}
