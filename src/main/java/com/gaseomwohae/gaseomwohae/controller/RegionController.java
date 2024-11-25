package com.gaseomwohae.gaseomwohae.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gaseomwohae.gaseomwohae.dto.region.LocationDto;
import com.gaseomwohae.gaseomwohae.service.RegionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/region")
@RequiredArgsConstructor
public class RegionController {
    private final RegionService regionService;

    @GetMapping("/cities")
    public List<String> getCities() {
        return regionService.getCities();
    }

    @GetMapping("/districts")
    public List<String> getDistricts(@RequestParam String city) {
        return regionService.getDistricts(city);
    }

    @GetMapping("/locations")
    public LocationDto getLocation(@RequestParam String city, @RequestParam String district) {
        return regionService.getLocation(city, district);
    }
}
