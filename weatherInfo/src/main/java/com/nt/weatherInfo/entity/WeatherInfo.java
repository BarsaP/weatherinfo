package com.nt.weatherInfo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WeatherInfo {
   
	private String description;
    private double temperature;
    private double humidity;
    private double windSpeed;
}
