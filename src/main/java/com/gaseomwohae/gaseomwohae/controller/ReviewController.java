package com.gaseomwohae.gaseomwohae.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gaseomwohae.gaseomwohae.dto.review.CreateReviewRequestDto;
import com.gaseomwohae.gaseomwohae.dto.review.ReviewDetailResponseDto;
import com.gaseomwohae.gaseomwohae.dto.review.UpdateReviewRequestDto;
import com.gaseomwohae.gaseomwohae.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public void createReview(@AuthenticationPrincipal Long userId, @RequestBody CreateReviewRequestDto createReviewRequestDto) {
        reviewService.createReview(userId, createReviewRequestDto);
    }

    @GetMapping
    public List<ReviewDetailResponseDto> getPlaceReviews(@RequestParam Long placeId) {
        return reviewService.getPlaceReviews(placeId);
    }

    @PutMapping("/{reviewId}")
    public void updateReview(@AuthenticationPrincipal Long userId, @PathVariable Long reviewId, @RequestBody UpdateReviewRequestDto updateReviewRequestDto) {
        reviewService.updateReview(userId, reviewId, updateReviewRequestDto);
    }

    @DeleteMapping("/{reviewId}")
    public void deleteReview(@AuthenticationPrincipal Long userId, @PathVariable Long reviewId) {
        reviewService.deleteReview(userId, reviewId);
    }   
}
