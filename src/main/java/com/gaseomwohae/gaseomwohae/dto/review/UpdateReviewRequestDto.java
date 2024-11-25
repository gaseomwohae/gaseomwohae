package com.gaseomwohae.gaseomwohae.dto.review;

import lombok.Getter;

@Getter
public class UpdateReviewRequestDto {
    private Byte rating;

    private String content;

    private String image;
}
