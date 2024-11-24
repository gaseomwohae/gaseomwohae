package com.gaseomwohae.gaseomwohae.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaseomwohae.gaseomwohae.dto.Travel;
import com.gaseomwohae.gaseomwohae.dto.travel.CreateTravelRequestDto;
import com.gaseomwohae.gaseomwohae.service.TravelService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/travel")
@RequiredArgsConstructor
public class TravelController {

	private final TravelService travelService;

	@GetMapping
	public List<Travel> getTravelList(@AuthenticationPrincipal Long userId) {
		return travelService.getTravelList(userId);
	}

	@PostMapping
	public void createTravel(@AuthenticationPrincipal Long userId,
		@RequestBody @Valid CreateTravelRequestDto createTravelRequestDto) {
		travelService.createTravel(userId, createTravelRequestDto);
	}

}