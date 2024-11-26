package com.gaseomwohae.gaseomwohae.service;

import java.util.List;

import com.gaseomwohae.gaseomwohae.dto.place.CreatePlaceRequestDto;
import com.gaseomwohae.gaseomwohae.dto.place.CreatePlaceResponseDto;

public interface PlaceService {
    List<CreatePlaceResponseDto> createPlace(List<CreatePlaceRequestDto> createPlaceRequestDto);
}

