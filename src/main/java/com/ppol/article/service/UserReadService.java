package com.ppol.article.service;

import org.springframework.stereotype.Service;

import com.ppol.article.dto.response.UserDto;
import com.ppol.article.entity.user.User;
import com.ppol.article.repository.jpa.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserReadService {

	// repositories
	private final UserRepository userRepository;

	public UserDto findUser(Long userId) {
		return UserDto.of(getUser(userId));
	}

	public User getUser(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("사용자"));
	}
}
