package com.ppol.alarm.service.user;

import org.springframework.stereotype.Service;

import com.ppol.alarm.entity.user.User;
import com.ppol.alarm.repository.jpa.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserReadService {

	// repository
	private final UserRepository userRepository;

	public User getUser(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("사용자"));
	}
}
