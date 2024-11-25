package com.gaseomwohae.gaseomwohae.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gaseomwohae.gaseomwohae.dto.region.LocationDto;

import javax.swing.plaf.synth.Region;

@Mapper
public interface RegionRepository {

    List<String> getCities();

    List<String> getDistricts(String city);

    LocationDto getLocation(String city, String district);
}
