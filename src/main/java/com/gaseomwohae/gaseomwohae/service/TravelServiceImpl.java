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
import com.gaseomwohae.gaseomwohae.dto.User;
import com.gaseomwohae.gaseomwohae.dto.travel.CreateTravelRequestDto;
import com.gaseomwohae.gaseomwohae.dto.travel.InviteParticipantRequestDto;
import com.gaseomwohae.gaseomwohae.dto.travel.TravelDetailResponseDto;
import com.gaseomwohae.gaseomwohae.dto.travel.UpdateTravelRequestDto;
import com.gaseomwohae.gaseomwohae.repository.ParticipantRepository;
import com.gaseomwohae.gaseomwohae.repository.ScheduleRepository;
import com.gaseomwohae.gaseomwohae.repository.TravelRepository;
import com.gaseomwohae.gaseomwohae.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TravelServiceImpl implements TravelService {
	private final TravelRepository travelRepository;
	private final ParticipantRepository participantRepository;
	private final ScheduleRepository scheduleRepository;
	private final UserRepository userRepository;
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
			Travel travel = travelRepository.findById(participant.getTravelId());
			if (travel != null) {
				travelList.add(travel);
			}
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

		travelRepository.insert(newTravel);

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

	@Override
	public void deleteTravel(Long userId, Long travelId) {
		Travel travel = travelRepository.findById(travelId);
		if (travel == null) {
			throw new BadRequestException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		List<Participant> participantList = participantRepository.findByTravelId(travelId);
		if (!participantList.stream().anyMatch(participant -> participant.getUserId().equals(userId))) {
			throw new BadRequestException(ErrorCode.ACCESS_DENIED);
		}

		travelRepository.delete(travelId);
	}

	@Override
	public void inviteParticipant(Long userId, InviteParticipantRequestDto inviteParticipantRequestDto) {
		Travel travel = travelRepository.findById(inviteParticipantRequestDto.getTravelId());
		if (travel == null) {
			throw new BadRequestException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		// 내가 참여중인 여행인지 확인
		Participant participant = participantRepository.findByTravelIdAndUserId(inviteParticipantRequestDto.getTravelId(), userId);
		if (participant == null) {
			throw new BadRequestException(ErrorCode.ACCESS_DENIED);
		}

		// 초대할 사람이 같은 여행에 참여중인지 확인
		Participant inviteParticipant = participantRepository.findByTravelIdAndUserId(inviteParticipantRequestDto.getTravelId(), inviteParticipantRequestDto.getUserId());
		if (inviteParticipant != null) {
			throw new BadRequestException(ErrorCode.RESOURCE_ALREADY_EXISTS);
		}

		// 초대할 사람의 대상 유저 찾기
		User user = userRepository.findById(inviteParticipantRequestDto.getUserId());
		if (user == null) {
			throw new BadRequestException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		// 대상 유저를 여행에 초대
		Participant newParticipant = Participant.builder()
			.travelId(inviteParticipantRequestDto.getTravelId())
			.userId(inviteParticipantRequestDto.getUserId())
			.build();

		participantRepository.insert(newParticipant);
	}
}
