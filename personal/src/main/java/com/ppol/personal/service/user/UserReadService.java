package com.ppol.personal.service.user;

import org.springframework.stereotype.Service;

import com.ppol.personal.entity.user.User;
import com.ppol.personal.entity.user.UserCharacter;
import com.ppol.personal.repository.UserCharacterRepository;
import com.ppol.personal.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 사용자 정보를 불러오는 기능들을 제공하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserReadService {

	// repository
	private final UserRepository userRepository;
	private final UserCharacterRepository characterRepository;

	/**
	 * 사용자 Entity를 불러오는 메서드, 예외처리 포함
	 */
	public User getUser(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("사용자"));
	}

	/**
	 * 사용자 캐릭터 Entity를 불러오는 메서드, 예외처리 포함
	 */
	public UserCharacter getUserCharacter(Long userId) {
		return characterRepository.findByUser_Id(userId).orElseThrow(() -> new EntityNotFoundException("캐릭터"));
	}
}
