package com.gaseomwohae.gaseomwohae.service;

import java.util.List;

import com.gaseomwohae.gaseomwohae.dto.Travel;
import com.gaseomwohae.gaseomwohae.dto.travel.CreateTravelRequestDto;

public interface TravelService {
	public List<Travel> getTravelList(Long userId);

	public void createTravel(Long userId, CreateTravelRequestDto createTravelRequestDto);
}
