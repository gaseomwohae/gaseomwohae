package com.gaseomwohae.gaseomwohae.mapper;

import com.gaseomwohae.gaseomwohae.dto.ReviewDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {
    List<ReviewDTO> findAll();
    ReviewDTO findById(Long id);
    List<ReviewDTO> findByUserId(Long userId);
    List<ReviewDTO> findByPlaceId(Long placeId);
    void insert(ReviewDTO review);
    void update(ReviewDTO review);
    void delete(Long id);
    void softDelete(Long id);
} 