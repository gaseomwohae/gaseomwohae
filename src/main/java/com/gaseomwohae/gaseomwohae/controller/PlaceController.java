package com.gaseomwohae.gaseomwohae.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaseomwohae.gaseomwohae.dto.place.CreatePlaceRequestDto;
import com.gaseomwohae.gaseomwohae.service.PlaceService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/place")
public class PlaceController {
	private final PlaceService placeService;

    @PostMapping
    public void createPlace(@RequestBody @Valid CreatePlaceRequestDto createPlaceRequestDto) {
        placeService.createPlace(createPlaceRequestDto);
    }
    
}
