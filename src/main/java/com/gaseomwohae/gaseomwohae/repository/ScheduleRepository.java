package com.gaseomwohae.gaseomwohae.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gaseomwohae.gaseomwohae.model.Schedule;

@Mapper
public interface ScheduleRepository {
	List<Schedule> findAll();

	Schedule findById(Long id);

	List<Schedule> findByTravelId(Long travelId);

	void insert(Schedule schedule);

	void update(Schedule schedule);

	void delete(Long id);
} 