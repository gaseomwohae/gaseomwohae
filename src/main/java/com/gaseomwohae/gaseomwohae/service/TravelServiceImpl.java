package com.gaseomwohae.gaseomwohae.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gaseomwohae.gaseomwohae.dto.Participant;
import com.gaseomwohae.gaseomwohae.dto.Travel;
import com.gaseomwohae.gaseomwohae.dto.User;
import com.gaseomwohae.gaseomwohae.dto.travel.CreateTravelRequestDto;
import com.gaseomwohae.gaseomwohae.repository.ParticipantRepository;
import com.gaseomwohae.gaseomwohae.repository.TravelRepository;
import com.gaseomwohae.gaseomwohae.repository.UserRepository;
import com.gaseomwohae.gaseomwohae.util.response.ErrorCode;
import com.gaseomwohae.gaseomwohae.util.response.exceptions.BadRequestException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TravelServiceImpl implements TravelService {
	private final TravelRepository travelRepository;
	private final ParticipantRepository participantRepository;
	private final UserRepository userRepository;

	@Override
	public List<Travel> getTravelList(Long userId) {

		User user = userRepository.findById(userId);
		if (user == null) {
			throw new BadRequestException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		List<Participant> participantList = participantRepository.findByUserId(userId);

		List<Travel> travelList = new ArrayList<>();
		participantList.forEach((participant -> {
			travelList.add(travelRepository.findById(participant.getTravelId()));
		}));
		return travelList;
	}

	@Override
	public void createTravel(Long userId, CreateTravelRequestDto createTravelRequestDto) {
		User user = userRepository.findById(userId);
		
		if (user == null) {
			throw new BadRequestException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		Long travelId = travelRepository.insert(Travel.builder()
			.name(createTravelRequestDto.getName())
			.destination(
				createTravelRequestDto.getDestination())
			.startDate(createTravelRequestDto.getStartDate())
			.endDate(createTravelRequestDto.getEndDate())
			.build()
		);

		participantRepository.insert(Participant.builder()
			.travelId(travelId)
			.userId(userId).
			build()
		);
	}
}
