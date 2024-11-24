package com.gaseomwohae.gaseomwohae.service;

import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gaseomwohae.gaseomwohae.common.exception.ErrorCode;
import com.gaseomwohae.gaseomwohae.common.exception.exceptions.BadRequestException;
import com.gaseomwohae.gaseomwohae.dto.Participant;
import com.gaseomwohae.gaseomwohae.dto.Place;
import com.gaseomwohae.gaseomwohae.dto.Schedule;
import com.gaseomwohae.gaseomwohae.dto.Travel;
import com.gaseomwohae.gaseomwohae.dto.schedule.CreateScheduleRequestDto;
import com.gaseomwohae.gaseomwohae.repository.ParticipantRepository;
import com.gaseomwohae.gaseomwohae.repository.PlaceRepository;
import com.gaseomwohae.gaseomwohae.repository.ScheduleRepository;
import com.gaseomwohae.gaseomwohae.repository.TravelRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
	private final ScheduleRepository scheduleRepository;
	private final TravelRepository travelRepository;
	private final PlaceRepository placeRepository;
	private final ParticipantRepository participantRepository;

	@Override
	public void createSchedule(Long userId, CreateScheduleRequestDto createScheduleRequestDto) {

		// 여행 존재 체크
		Travel travel = travelRepository.findById(createScheduleRequestDto.getTravelId());
		if (travel == null) {
			throw new BadRequestException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		// 장소 존재 체크
		Place place = placeRepository.findById(createScheduleRequestDto.getPlaceId());
		if (place == null) {
			throw new BadRequestException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		// 여행 참여자인지 체크
		Participant participant = participantRepository.findByTravelIdAndUserId(createScheduleRequestDto.getTravelId(),
			userId);
		if (participant == null) {
			throw new BadRequestException(ErrorCode.ACCESS_DENIED);
		}
		
		// 일정 겹치는지 체크
		List<Schedule> scheduleList = scheduleRepository.findByTravelId(createScheduleRequestDto.getTravelId());

		boolean hasOverlap = scheduleList.stream()
			.filter(schedule -> schedule.getDate().equals(createScheduleRequestDto.getDate()))
			.anyMatch(schedule -> checkOverlap(schedule.getStartTime(), schedule.getEndTime(),
				createScheduleRequestDto.getStartTime(), createScheduleRequestDto.getEndTime()));
		
		if (hasOverlap) {
			throw new BadRequestException(ErrorCode.INVALID_INPUT);
		}

		// 30분 간격인지 체크
		if (!checkInterval(createScheduleRequestDto.getStartTime(), createScheduleRequestDto.getEndTime())) {
			throw new BadRequestException(ErrorCode.INVALID_INPUT);
		}


		Schedule schedule = Schedule.builder()
			.travelId(createScheduleRequestDto.getTravelId())
			.placeId(createScheduleRequestDto.getPlaceId())
			.date(createScheduleRequestDto.getDate())
			.startTime(createScheduleRequestDto.getStartTime())
			.endTime(createScheduleRequestDto.getEndTime())
			.build();

		scheduleRepository.insert(schedule);
	}

	// 일정이 겹치는지 체크
	private boolean checkOverlap(LocalTime startTime1, LocalTime endTime1, LocalTime startTime2, LocalTime endTime2) {
		return endTime1.isAfter(startTime2) && startTime1.isBefore(endTime2);
	}

	// 30분 간격인지 체크
	private boolean checkInterval(LocalTime startTime, LocalTime endTime) {
		return startTime.getMinute() % 30 == 0 && endTime.getMinute() % 30 == 0;
	}
}
