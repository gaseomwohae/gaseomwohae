package com.gaseomwohae.gaseomwohae.dto.review;

import java.time.LocalDateTime;

import com.gaseomwohae.gaseomwohae.dto.user.GetUserInfoResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ReviewDetailResponseDto {
    private Long id;
    private Long placeId;
    private Byte rating;
    private String content;
    private String image;
    private LocalDateTime createdAt;
    private GetUserInfoResponseDto user;
}
