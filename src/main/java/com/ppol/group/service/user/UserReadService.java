package com.ppol.group.service.user;

import org.springframework.stereotype.Service;

import com.ppol.group.entity.user.User;
import com.ppol.group.repository.jpa.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 사용자 정보를 불러오는 기능을 제공하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserReadService {

	// repository
	private final UserRepository userRepository;

	/**
	 * 사용자 기본 정보를 불러오는 메서드, 예외처리 포함
	 */
	public User getUser(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("사용자"));
	}
}
