package com.nt.weatherInfo.service;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nt.weatherInfo.entity.LatLng;

@Service
public class GeocodingService {
	private final String apiKey = "37a9262c52308c32ca18ee609c49cb82";

    public LatLng getLatLongFromPincode(String pincode) {
        String geocodingUrl = "http://api.openweathermap.org/geo/1.0/zip?zip=" + pincode + ",IN&appid=" + apiKey;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(geocodingUrl, String.class);
        // Parse the response to get lat and lon
        
        JSONObject jsonObject = new JSONObject(response.getBody());
        return new LatLng(jsonObject.getDouble("lat"), jsonObject.getDouble("lon"));
    }
}
