package com.gaseomwohae.gaseomwohae.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gaseomwohae.gaseomwohae.model.Participant;

@Mapper
public interface ParticipantRepository {
	List<Participant> findAll();

	Participant findById(Long id);

	List<Participant> findByTravelId(Long travelId);

	List<Participant> findByUserId(Long userId);

	Participant findByTravelIdAndUserId(Long travelId, Long userId);

	void insert(Participant participant);

	void update(Participant participant);

	void delete(Long id);
} 