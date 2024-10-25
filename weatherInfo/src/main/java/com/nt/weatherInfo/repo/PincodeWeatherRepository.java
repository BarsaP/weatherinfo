package com.nt.weatherInfo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.weatherInfo.entity.PincodeWeather;

public interface PincodeWeatherRepository extends JpaRepository<PincodeWeather, Long>{
	Optional<PincodeWeather> findByPincodeAndDate(String pincode, String date);
}
