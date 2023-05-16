package com.ppol.personal.service.user;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ppol.personal.entity.user.User;
import com.ppol.personal.entity.user.UserCharacter;
import com.ppol.personal.repository.UserCharacterRepository;
import com.ppol.personal.repository.UserRepository;
import com.ppol.personal.util.constatnt.enums.CharacterColor;
import com.ppol.personal.util.constatnt.enums.FaceType;
import com.ppol.personal.util.constatnt.enums.MeshType;

import jakarta.persistence.EntityNotFoundException;
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
	 * 개인 룸에 표시해줄 npc 목록을 불러오는 메서드
	 */
	public List<UserCharacter> getNpcList(Long userId) {

		List<UserCharacter> characterList = characterRepository.findFollowingsCharacters(userId);

		Collections.shuffle(characterList);

		return characterList.subList(0, Math.min(characterList.size(), 5));
	}

	/**
	 * 사용자 Entity를 불러오는 메서드, 예외처리 포함
	 */
	public User getUser(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("사용자"));
	}

	/**
	 * 사용자 캐릭터 Entity를 불러오는 메서드
	 */
	public UserCharacter getUserCharacter(Long userId) {
		return characterRepository.findByUser_Id(userId).orElseGet(() -> createUserCharacter(userId));
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
