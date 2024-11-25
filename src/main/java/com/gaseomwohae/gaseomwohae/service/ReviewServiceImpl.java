package com.gaseomwohae.gaseomwohae.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.gaseomwohae.gaseomwohae.common.exception.ErrorCode;
import com.gaseomwohae.gaseomwohae.common.exception.exceptions.BadRequestException;
import com.gaseomwohae.gaseomwohae.dto.review.CreateReviewRequestDto;
import com.gaseomwohae.gaseomwohae.dto.review.ReviewDetailResponseDto;
import com.gaseomwohae.gaseomwohae.dto.review.UpdateReviewRequestDto;
import com.gaseomwohae.gaseomwohae.dto.user.GetUserInfoResponseDto;
import com.gaseomwohae.gaseomwohae.model.Place;
import com.gaseomwohae.gaseomwohae.model.Review;
import com.gaseomwohae.gaseomwohae.model.User;
import com.gaseomwohae.gaseomwohae.repository.PlaceRepository;
import com.gaseomwohae.gaseomwohae.repository.ReviewRepository;
import com.gaseomwohae.gaseomwohae.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;

    public void createReview(Long userId, CreateReviewRequestDto createReviewRequestDto) {
        Place place = placeRepository.findById(createReviewRequestDto.getPlaceId());
        if(place == null) {
            throw new BadRequestException(ErrorCode.RESOURCE_NOT_FOUND);
        }

        reviewRepository.insert(Review.builder()
            .userId(userId)
            .placeId(createReviewRequestDto.getPlaceId())
            .rating(createReviewRequestDto.getRating())
            .content(createReviewRequestDto.getContent())
            .image(createReviewRequestDto.getImage())
            .build());
    }

    public List<ReviewDetailResponseDto> getPlaceReviews(Long placeId) {
        Place place = placeRepository.findById(placeId);
        if(place == null) {
            throw new BadRequestException(ErrorCode.RESOURCE_NOT_FOUND);
        }

        List<Review> reviews = reviewRepository.findByPlaceId(placeId);
        return reviews.stream().map(review -> {
            User user = userRepository.findById(review.getUserId());
            if(user == null) {
                throw new BadRequestException(ErrorCode.RESOURCE_NOT_FOUND);
            }

            return ReviewDetailResponseDto.builder()
            .id(review.getId())
            .placeId(placeId)
            .rating(review.getRating())
            .content(review.getContent())
            .image(review.getImage())
            .createdAt(review.getCreatedAt())
            .user(
                GetUserInfoResponseDto
                .builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .profileImage(user.getProfileImage())
                .createdAt(user.getCreatedAt())
                .build()
            ).build();
        }).collect(Collectors.toList());
    }

    public void updateReview(Long userId, Long reviewId, UpdateReviewRequestDto updateReviewRequestDto) {
        Review review = reviewRepository.findById(reviewId);
        if(review == null) {
            throw new BadRequestException(ErrorCode.RESOURCE_NOT_FOUND);
        }

        if(review.getUserId() != userId) {
            throw new BadRequestException(ErrorCode.ACCESS_DENIED);
        }

        reviewRepository.update(Review.builder()
            .rating(updateReviewRequestDto.getRating() != null ? updateReviewRequestDto.getRating() : review.getRating())
            .content(updateReviewRequestDto.getContent() != null ? updateReviewRequestDto.getContent() : review.getContent())
            .image(updateReviewRequestDto.getImage() != null ? updateReviewRequestDto.getImage() : review.getImage())
            .build());
    }

    public void deleteReview(Long userId, Long reviewId) {
        Review review = reviewRepository.findById(reviewId);
        if(review == null) {
            throw new BadRequestException(ErrorCode.RESOURCE_NOT_FOUND);
        }

        if(review.getUserId() != userId) {
            throw new BadRequestException(ErrorCode.ACCESS_DENIED);
        }

        reviewRepository.delete(reviewId);
    }
}