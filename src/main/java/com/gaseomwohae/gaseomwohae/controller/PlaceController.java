package com.gaseomwohae.gaseomwohae.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaseomwohae.gaseomwohae.dto.place.CreatePlaceRequestDto;
import com.gaseomwohae.gaseomwohae.dto.place.CreatePlaceResponseDto;
import com.gaseomwohae.gaseomwohae.service.PlaceService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/place")
public class PlaceController {
	private final PlaceService placeService;

    @PostMapping
    public List<CreatePlaceResponseDto> createPlace(@RequestBody @Valid List<CreatePlaceRequestDto> createPlaceRequestDto) {
        return placeService.createPlace(createPlaceRequestDto);
    }
    
}
