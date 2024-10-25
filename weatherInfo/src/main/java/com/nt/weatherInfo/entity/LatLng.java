package com.nt.weatherInfo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LatLng {
  
	private double lat;
    private double lon;
    
    
	@Override
	public String toString() {
		return "LatLng [lat=" + lat + ", lon=" + lon + "]";
	}
    
}
