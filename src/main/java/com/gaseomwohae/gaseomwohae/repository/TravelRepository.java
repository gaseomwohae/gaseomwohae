package com.gaseomwohae.gaseomwohae.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gaseomwohae.gaseomwohae.dto.Travel;

@Mapper
public interface TravelRepository {
	List<Travel> findAll();

	Travel findById(Long id);

	void insert(Travel travel);

	void update(Travel travel);

	void delete(Long id);

	void softDelete(Long id);
} 