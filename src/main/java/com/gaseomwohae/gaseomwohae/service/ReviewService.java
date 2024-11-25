package com.gaseomwohae.gaseomwohae.service;

import java.util.List;

import com.gaseomwohae.gaseomwohae.dto.review.CreateReviewRequestDto;
import com.gaseomwohae.gaseomwohae.dto.review.ReviewDetailResponseDto;
import com.gaseomwohae.gaseomwohae.dto.review.UpdateReviewRequestDto;

public interface ReviewService {
    void createReview(Long userId, CreateReviewRequestDto createReviewRequestDto);

    List<ReviewDetailResponseDto> getPlaceReviews(Long placeId);

    void updateReview(Long userId, Long reviewId, UpdateReviewRequestDto updateReviewRequestDto);

    void deleteReview(Long userId, Long reviewId);
}
