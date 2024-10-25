package com.nt.weatherInfo.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nt.weatherInfo.entity.WeatherInfo;

@Service
public class WeatherService {
	private final String apiKey = "your_api_key";

    public WeatherInfo getWeatherByLatLong(double lat, double lon, String date) {
        String weatherUrl = "http://api.openweathermap.org/data/2.5/forecast?lat=" + lat + "&lon=" + lon + "&appid=" + apiKey + "&units=metric";
        
        //for debug
        System.out.println("Weather URL: " + weatherUrl);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(weatherUrl, String.class);
        
        // Parse the response to extract weather data for the required date
        JSONObject jsonObject = new JSONObject(response.getBody());
        JSONArray listArray = jsonObject.getJSONArray("list");
        JSONObject dailyWeather = null;
        for (int i = 0; i < listArray.length(); i++) {
            JSONObject weatherItem = listArray.getJSONObject(i);
            String forecastDate = weatherItem.getString("dt_txt").split(" ")[0];
            
            if (forecastDate.equals(date)) {
                dailyWeather = weatherItem;
                break;
            }
        }

        if (dailyWeather != null) {
            JSONObject main = dailyWeather.getJSONObject("main");
            JSONArray weatherArray = dailyWeather.getJSONArray("weather");
            JSONObject weatherObject = weatherArray.getJSONObject(0);

            String description = weatherObject.getString("description");
            double temperature = main.getDouble("temp");
            double humidity = main.getDouble("humidity");
            double windSpeed = dailyWeather.getJSONObject("wind").getDouble("speed");

            return new WeatherInfo(description, temperature, humidity, windSpeed);
        } else {
            throw new RuntimeException("No weather data found for the specified date.");
        }
    }
}
