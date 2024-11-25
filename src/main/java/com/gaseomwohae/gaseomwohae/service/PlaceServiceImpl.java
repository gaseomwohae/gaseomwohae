package com.gaseomwohae.gaseomwohae.service;

import org.springframework.stereotype.Service;

import com.gaseomwohae.gaseomwohae.common.exception.ErrorCode;
import com.gaseomwohae.gaseomwohae.common.exception.exceptions.BadRequestException;
import com.gaseomwohae.gaseomwohae.dto.place.CreatePlaceRequestDto;
import com.gaseomwohae.gaseomwohae.model.Place;
import com.gaseomwohae.gaseomwohae.repository.PlaceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {
    private final PlaceRepository placeRepository;

    public void createPlace(CreatePlaceRequestDto createPlaceRequestDto) {
        Place place = placeRepository.findById(createPlaceRequestDto.getId());
        if(place != null) {
            throw new BadRequestException(ErrorCode.RESOURCE_ALREADY_EXISTS);
        }

        placeRepository.insert(Place.builder()
            .id(createPlaceRequestDto.getId())
            .name(createPlaceRequestDto.getName())
            .category(createPlaceRequestDto.getCategory())
            .address(createPlaceRequestDto.getAddress())
            .roadAddress(createPlaceRequestDto.getRoadAddress())
            .thumbnail(createPlaceRequestDto.getThumbnail())
            .phone(createPlaceRequestDto.getPhone())
            .url(createPlaceRequestDto.getUrl())
            .x(createPlaceRequestDto.getX())
            .y(createPlaceRequestDto.getY())
            .build());
    }
}
