package com.gaseomwohae.gaseomwohae.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaseomwohae.gaseomwohae.common.exception.ErrorCode;
import com.gaseomwohae.gaseomwohae.common.exception.exceptions.BadRequestException;
import com.gaseomwohae.gaseomwohae.dto.Participant;
import com.gaseomwohae.gaseomwohae.dto.Schedule;
import com.gaseomwohae.gaseomwohae.dto.Travel;
import com.gaseomwohae.gaseomwohae.dto.travel.CreateTravelRequestDto;
import com.gaseomwohae.gaseomwohae.dto.travel.TravelDetailResponseDto;
import com.gaseomwohae.gaseomwohae.dto.travel.UpdateTravelRequestDto;
import com.gaseomwohae.gaseomwohae.repository.ParticipantRepository;
import com.gaseomwohae.gaseomwohae.repository.ScheduleRepository;
import com.gaseomwohae.gaseomwohae.repository.TravelRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TravelServiceImpl implements TravelService {
	private final TravelRepository travelRepository;
	private final ParticipantRepository participantRepository;
	private final ScheduleRepository scheduleRepository;

	@Override
	public TravelDetailResponseDto getTravel(Long userId, Long travelId) {
		Travel travel = travelRepository.findById(travelId);
		if (travel == null) {
			throw new BadRequestException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		List<Participant> participantList = participantRepository.findByTravelId(travelId);
		if (!participantList.stream().anyMatch(participant -> participant.getUserId().equals(userId))) {
			throw new BadRequestException(ErrorCode.ACCESS_DENIED);
		}

		List<Schedule> scheduleList = scheduleRepository.findByTravelId(travelId);

		return TravelDetailResponseDto.builder()
			.travel(travel)
			.participants(participantList)
			.schedules(scheduleList)
			.build();
	}

	@Override
	public List<Travel> getTravelList(Long userId) {
		List<Participant> participantList = participantRepository.findByUserId(userId);

		List<Travel> travelList = new ArrayList<>();
		participantList.forEach((participant -> {
			travelList.add(travelRepository.findById(participant.getTravelId()));
		}));
		return travelList;
	}

	@Override
	@Transactional
	public void createTravel(Long userId, CreateTravelRequestDto createTravelRequestDto) {

		Travel newTravel = Travel.builder()
			.name(createTravelRequestDto.getName())
			.destination(
				createTravelRequestDto.getDestination())
			.startDate(createTravelRequestDto.getStartDate())
			.endDate(createTravelRequestDto.getEndDate())
			.build();

		Participant participant = Participant.builder()
			.travelId(newTravel.getId())
			.userId(userId).
			build();

		participantRepository.insert(
			participant
		);
	}

	@Override
	public void updateTravel(Long travelId, UpdateTravelRequestDto updateTravelRequestDto) {

		Travel travel = travelRepository.findById(travelId);

		if (travel == null) {
			throw new BadRequestException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		Travel updateTravel = Travel.builder().
			id(travel.getId())
			.name(updateTravelRequestDto.getName() == null ? travel.getName() : updateTravelRequestDto.getName())
			.destination(updateTravelRequestDto.getDestination() == null ? travel.getDestination() :
				updateTravelRequestDto.getDestination())
			.startDate(updateTravelRequestDto.getStartDate() == null ? travel.getStartDate() :
				updateTravelRequestDto.getStartDate())
			.endDate(updateTravelRequestDto.getEndDate() == null ? travel.getEndDate() :
				updateTravelRequestDto.getEndDate())
			.build();

		travelRepository.update(updateTravel);
	}
}
