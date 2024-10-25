package com.nt.weatherInfo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class PincodeWeather {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String pincode;
	    private double lat;
	    private double lon;
	    private String date;
	    private String weatherData; // Store the weather info as JSON or separate fields

	    // Getters and setters
}
