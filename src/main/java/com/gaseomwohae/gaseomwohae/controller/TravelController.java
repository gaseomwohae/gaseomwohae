package com.gaseomwohae.gaseomwohae.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaseomwohae.gaseomwohae.dto.Travel;
import com.gaseomwohae.gaseomwohae.dto.travel.CreateTravelRequestDto;
import com.gaseomwohae.gaseomwohae.service.TravelService;
import com.gaseomwohae.gaseomwohae.util.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/travel")
@RequiredArgsConstructor
public class TravelController {

	private final TravelService travelService;

	@GetMapping
	public ResponseEntity<ApiResponse<List<Travel>>> getTravelList(@AuthenticationPrincipal Long userId) {
		return ResponseEntity.ok(ApiResponse.success(travelService.getTravelList(userId)));
	}

	@PostMapping
	public ResponseEntity<ApiResponse<Void>> createTravel(@AuthenticationPrincipal Long userId,
		@RequestBody CreateTravelRequestDto createTravelRequestDto) {
		travelService.createTravel(userId, createTravelRequestDto);
		return ResponseEntity.ok(ApiResponse.success());
	}
}