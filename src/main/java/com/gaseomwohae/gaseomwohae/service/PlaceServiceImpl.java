package com.gaseomwohae.gaseomwohae.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gaseomwohae.gaseomwohae.dto.place.CreatePlaceRequestDto;
import com.gaseomwohae.gaseomwohae.dto.place.CreatePlaceResponseDto;
import com.gaseomwohae.gaseomwohae.model.Place;
import com.gaseomwohae.gaseomwohae.model.Review;
import com.gaseomwohae.gaseomwohae.repository.PlaceRepository;
import com.gaseomwohae.gaseomwohae.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {
    private final PlaceRepository placeRepository;
    private final ReviewRepository reviewRepository;

    public List<CreatePlaceResponseDto> createPlace(List<CreatePlaceRequestDto> createPlaceRequestDto) {
        List<CreatePlaceResponseDto> ratings = new ArrayList<>();
        createPlaceRequestDto.forEach(request -> {
            Double rating = 0.0;

            
            // 이미 데이터가 존재하면
            Place place = placeRepository.findById(request.getId());
            if(place != null) {
                List<Review> reviews = reviewRepository.findByPlaceId(place.getId());
                rating = reviews.stream().mapToDouble(Review::getRating).average().orElse(0);
                ratings.add(CreatePlaceResponseDto.builder()
                    .id(place.getId())
                    .rating(rating)
                    .build()    
                );

                return;
            }

            placeRepository.insert(Place.builder()
                .id(request.getId())
                .name(request.getName())
                .category(request.getCategory())
                .address(request.getAddress())
                .roadAddress(request.getRoadAddress())
                .thumbnail(request.getThumbnail())
                .phone(request.getPhone())
                .url(request.getUrl())
                .x(request.getX())
                .y(request.getY())
                .build());

            ratings.add(CreatePlaceResponseDto.builder()
                .id(request.getId())
                .rating(rating)
                .build()
            );
            
        });

        return ratings;
    }
}
