package com.gaseomwohae.gaseomwohae.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gaseomwohae.gaseomwohae.model.Review;

@Mapper
public interface ReviewRepository {
	List<Review> findAll();

	Review findById(Long id);

	List<Review> findByUserId(Long userId);

	List<Review> findByPlaceId(Long placeId);

	void insert(Review review);

	void update(Review review);

	void delete(Long id);

	void softDelete(Long id);
} 