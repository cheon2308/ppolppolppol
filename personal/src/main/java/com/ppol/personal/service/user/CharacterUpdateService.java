package com.ppol.personal.service.user;

import org.springframework.stereotype.Service;

import com.ppol.personal.dto.request.CharacterDto;
import com.ppol.personal.entity.user.UserCharacter;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 사용자 캐릭터 정보를 업데이트 하는 기능들을 하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CharacterUpdateService {

	// service
	private final UserReadService userReadService;

	/**
	 * 캐릭터 Face Type, Mesh Type, Color 업데이트 메서드
	 */
	@Transactional
	public void updateCharacter(Long userId, CharacterDto characterDto) {

		log.info("{} : {}", userId, characterDto);

		UserCharacter userCharacter = userReadService.getUserCharacter(userId);

		userCharacter.updateFaceType(characterDto.getFaceType());
		userCharacter.updateColor(characterDto.getColor());
		userCharacter.updateMeshType(characterDto.getMeshType());
	}
}
