package com.gaseomwohae.gaseomwohae.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gaseomwohae.gaseomwohae.model.Place;

@Mapper
public interface PlaceRepository {
	List<Place> findAll();

	Place findById(Long id);

	void insert(Place place);

	void update(Place place);

	void delete(Long id);
} 