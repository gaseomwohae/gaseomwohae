package com.gaseomwohae.gaseomwohae.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gaseomwohae.gaseomwohae.common.exception.ErrorCode;
import com.gaseomwohae.gaseomwohae.common.exception.exceptions.BadRequestException;
import com.gaseomwohae.gaseomwohae.dto.User;
import com.gaseomwohae.gaseomwohae.dto.user.GetUserInfoResponseDto;
import com.gaseomwohae.gaseomwohae.dto.user.SignUpRequestDto;
import com.gaseomwohae.gaseomwohae.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	public void signUp(SignUpRequestDto signUpRequestDto) {
		User user = userRepository.findByEmail(signUpRequestDto.getEmail());
		if (user != null) {
			throw new BadRequestException(ErrorCode.DUPLICATE_EMAIL);
		}

		userRepository.insert(User.builder()
			.name(signUpRequestDto.getName())
			.email(signUpRequestDto.getEmail())
			.password(passwordEncoder.encode(signUpRequestDto.getPassword()))
			.profileImage(signUpRequestDto.getProfileImage())
			.build()
		);
	}

	@Override
	public GetUserInfoResponseDto getUserInfo(Long userId) {
		User user = userRepository.findById(userId);

		return GetUserInfoResponseDto.builder()
			.id(user.getId())
			.name(user.getName())
			.email(user.getEmail())
			.profileImage(user.getProfileImage())
			.createdAt(user.getCreatedAt())
			.build();
	}

}


