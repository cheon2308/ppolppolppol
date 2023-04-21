package com.ppol.message.service;

import org.springframework.stereotype.Service;

import com.ppol.message.dto.response.UserDto;
import com.ppol.message.entity.user.User;
import com.ppol.message.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserReadService {

	private final UserRepository userRepository;

	public UserDto findUser(Long userId) {
		return UserDto.of(getUser(userId));
	}

	public User getUser(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("사용자"));
	}
}
