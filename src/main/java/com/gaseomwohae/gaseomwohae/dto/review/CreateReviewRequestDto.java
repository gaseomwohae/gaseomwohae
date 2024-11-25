package com.gaseomwohae.gaseomwohae.dto.review;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateReviewRequestDto {
    @NotNull
    private Long placeId;

    @NotNull
    private Byte rating;

    @NotNull
    private String content;

    @NotNull
    private String image;
}
