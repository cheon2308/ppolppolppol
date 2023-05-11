package com.ppol.onlineserver.service;

import org.springframework.stereotype.Service;

import com.ppol.onlineserver.entity.User;
import com.ppol.onlineserver.entity.UserCharacter;
import com.ppol.onlineserver.repository.UserCharacterRepository;
import com.ppol.onlineserver.repository.UserRepository;
import com.ppol.onlineserver.util.constant.enums.CharacterColor;
import com.ppol.onlineserver.util.constant.enums.FaceType;
import com.ppol.onlineserver.util.constant.enums.MeshType;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 사용자 캐릭터 불러오는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CharacterReadService {

	// repository
	private final UserCharacterRepository characterRepository;
	private final UserRepository userRepository;

	/**
	 * 사용자 캐릭터 Entity를 불러오는 메서드
	 */
	public UserCharacter getUserCharacter(Long userId) {
		return characterRepository.findByUser_Id(userId).orElseGet(() -> createUserCharacter(userId));
	}

	/**
	 * 사용자 Entity를 불러오는 메서드, 예외처리 포함
	 */
	public User getUser(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("사용자"));
	}

	private UserCharacter createUserCharacter(Long userId) {

		return characterRepository.save(UserCharacter.builder()
			.user(getUser(userId))
			.color(CharacterColor.RED)
			.faceType(FaceType.ONE)
			.meshType(MeshType.BASIC)
			.build());
	}
}
