package com.gaseomwohae.gaseomwohae.service;

import java.util.List;

import com.gaseomwohae.gaseomwohae.dto.Travel;

public interface TravelService {
	public List<Travel> getTravelList(Long userId);
}
