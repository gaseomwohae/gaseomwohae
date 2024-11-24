package com.gaseomwohae.gaseomwohae.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gaseomwohae.gaseomwohae.dto.User;
import com.gaseomwohae.gaseomwohae.dto.user.SignUpRequestDto;
import com.gaseomwohae.gaseomwohae.repository.UserRepository;
import com.gaseomwohae.gaseomwohae.util.response.ErrorCode;
import com.gaseomwohae.gaseomwohae.util.response.exceptions.BadRequestException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	public void signUp(SignUpRequestDto signUpRequestDto) {

		User user = userRepository.findByEmail
			(signUpRequestDto.getEmail());
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

}
