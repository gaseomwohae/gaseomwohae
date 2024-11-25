package com.gaseomwohae.gaseomwohae.service;

import java.util.List;

import com.gaseomwohae.gaseomwohae.dto.region.LocationDto;


public interface RegionService {
    public List<String> getCities();

    public List<String> getDistricts(String city);
    
    public LocationDto getLocation(String city, String district);
}
