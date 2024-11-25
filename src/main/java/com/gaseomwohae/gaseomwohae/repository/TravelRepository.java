package com.gaseomwohae.gaseomwohae.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gaseomwohae.gaseomwohae.model.Travel;

@Mapper
public interface TravelRepository {
	List<Travel> findAll();

	Travel findById(Long id);

	Long insert(Travel travel);

	void update(Travel travel);

	void delete(Long id);
} 