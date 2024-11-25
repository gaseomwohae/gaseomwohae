package com.gaseomwohae.gaseomwohae.service;

import java.util.List;

import com.gaseomwohae.gaseomwohae.common.exception.ErrorCode;
import com.gaseomwohae.gaseomwohae.common.exception.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

import com.gaseomwohae.gaseomwohae.dto.region.LocationDto;
import com.gaseomwohae.gaseomwohae.repository.RegionRepository;

import lombok.RequiredArgsConstructor;

import javax.swing.plaf.synth.Region;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;

    @Override
    public List<String> getCities() {
        return regionRepository.getCities();
    }

    @Override
    public List<String> getDistricts(String city) {
        return regionRepository.getDistricts(city);
    }

    @Override
    public LocationDto getLocation(String city, String district) {
        return regionRepository.getLocation(city, district);
    }
}
