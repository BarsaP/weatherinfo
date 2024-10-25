package com.nt.weatherInfo.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nt.weatherInfo.entity.WeatherInfo;

@Service
public class WeatherService {
	private final String apiKey = "37a9262c52308c32ca18ee609c49cb82";

    public WeatherInfo getWeatherByLatLong(double lat, double lon, String date) {
        String weatherUrl = "http://api.openweathermap.org/data/2.5/forecast?lat=" + lat + "&lon=" + lon + "&appid=" + apiKey + "&units=metric";
        //https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/414004/2020-06-20 + "?unitGroup=metric&key=" + apiKey;
        
        //for debug
        System.out.println("Weather URL: " + weatherUrl);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(weatherUrl, String.class);
        
        // Parse the response to extract weather data for the required date
        JSONObject jsonObject = new JSONObject(response.getBody());
        JSONArray listArray = jsonObject.getJSONArray("list");
//     // Get daily weather (One Call API gives an array for daily weather)
//        JSONArray dailyArray = jsonObject.getJSONArray("daily");
//        
//     // Find the appropriate day's weather (e.g., the first one for today)
//        JSONObject dailyWeather = dailyArray.getJSONObject(0);
//        JSONArray weatherArray = jsonObject.getJSONArray("weather");
//        JSONObject weatherObject = weatherArray.getJSONObject(0);
//
//        String description = weatherObject.getString("description");
//        double temperature = jsonObject.getJSONObject("temp").getDouble("day");
//        double humidity = dailyWeather.getDouble("humidity");
//        double windSpeed = dailyWeather.getDouble("wind");
//        // Save the weather data to the database
//        return new WeatherInfo(description, temperature, humidity, windSpeed); // Parse the weather info
        JSONObject dailyWeather = null;
        for (int i = 0; i < listArray.length(); i++) {
            JSONObject weatherItem = listArray.getJSONObject(i);
            String forecastDate = weatherItem.getString("dt_txt").split(" ")[0]; // Extract date part
            
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
