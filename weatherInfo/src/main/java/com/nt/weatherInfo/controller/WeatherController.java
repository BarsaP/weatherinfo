package com.nt.weatherInfo.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nt.weatherInfo.entity.LatLng;
import com.nt.weatherInfo.entity.PincodeWeather;
import com.nt.weatherInfo.entity.WeatherInfo;
import com.nt.weatherInfo.repo.PincodeWeatherRepository;
import com.nt.weatherInfo.service.GeocodingService;
import com.nt.weatherInfo.service.WeatherService;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
  
	private final GeocodingService geocodingService;
    private final WeatherService weatherService;
    private final PincodeWeatherRepository weatherRepository;

    public WeatherController(GeocodingService geocodingService, WeatherService weatherService, PincodeWeatherRepository weatherRepository) {
        this.geocodingService = geocodingService;
        this.weatherService = weatherService;
        this.weatherRepository = weatherRepository;
    }

    @GetMapping
    public ResponseEntity<String> getWeather(@RequestParam String pincode, @RequestParam String date) {
        Optional<PincodeWeather> existingWeather = weatherRepository.findByPincodeAndDate(pincode, date);
        
        if (existingWeather.isPresent()) {
            return ResponseEntity.ok(existingWeather.get().getWeatherData());
        } else {
            // Fetch lat/long using geocoding API
            LatLng latLng = geocodingService.getLatLongFromPincode(pincode);
            
            // Fetch weather using OpenWeather API
            WeatherInfo weatherInfo = weatherService.getWeatherByLatLong(latLng.getLat(), latLng.getLon(), date);
            
            // Save the weather info to DB
            PincodeWeather weather = new PincodeWeather();
            weather.setPincode(pincode);
            weather.setLat(latLng.getLat());
            weather.setLon(latLng.getLon());
            weather.setDate(date);
            weather.setWeatherData(weatherInfo.toString()); // Convert WeatherInfo to JSON or string
            
            weatherRepository.save(weather);
            
            return ResponseEntity.ok(weatherInfo.toString());
        }
    }
}
