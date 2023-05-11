package com.ppol.onlineserver.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.ppol.onlineserver.dto.request.EnterDto;
import com.ppol.onlineserver.dto.request.MoveDto;
import com.ppol.onlineserver.dto.request.TypeUpdateDto;
import com.ppol.onlineserver.dto.response.CharacterDto;
import com.ppol.onlineserver.dto.response.CharacterLocation;
import com.ppol.onlineserver.dto.response.CharacterRotation;
import com.ppol.onlineserver.dto.response.CharacterType;
import com.ppol.onlineserver.entity.UserCharacter;
import com.ppol.onlineserver.util.constant.enums.EventType;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 캐릭터들의 이동, 연결, 연결끊김, 외형변경등의 요청들을 받아서 해당 그룹의 캐릭터 DTO들을 저장하는 기능을 하기위한 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CharacterUpdateService {

	// service
	private final CharacterReadService characterReadService;

	// others
	private final RedisTemplate<String, Set<CharacterDto>> redisTemplate;

	/**
	 * 그룹 방에 입장 시
	 */
	@Transactional
	public CharacterDto enterGroup(Long groupId, EnterDto enterDto) {

		UserCharacter userCharacter = characterReadService.getUserCharacter(enterDto.getUserId());

		CharacterDto character = CharacterDto.builder()
			.userId(enterDto.getUserId())
			.username(userCharacter.getUser().getUsername())
			.type(getType(userCharacter))
			.location(getDefaultLocation())
			.rotation(getDefaultRotation())
			.build();

		Set<CharacterDto> characterSet = getCharacterSet(groupId);
		characterSet.add(character);

		setCharacterSet(groupId, characterSet);

		character.setEventType(EventType.ENTER);

		return character;
	}

	/**
	 * 그룹 방에서 퇴장 시
	 */
	@Transactional
	public CharacterDto leaveGroup(Long groupId, Long userId) {

		Set<CharacterDto> characterSet = getCharacterSet(groupId);

		CharacterDto character = characterSet.stream()
			.filter(c -> c.getUserId().equals(userId))
			.findAny()
			.orElseThrow();

		characterSet.remove(character);

		setCharacterSet(groupId, characterSet);

		character.setEventType(EventType.LEAVE);

		return character;
	}

	/**
	 * 캐릭터 이동 시
	 */
	@Transactional
	public CharacterDto moveCharacter(Long groupId, MoveDto moveDto) {

		Set<CharacterDto> characterSet = getCharacterSet(groupId);

		CharacterDto character = characterSet.stream()
			.filter(c -> c.getUserId().equals(moveDto.getUserId()))
			.findAny()
			.orElseThrow();

		updateLocation(character, moveDto);

		setCharacterSet(groupId, characterSet);

		character.setEventType(EventType.MOVE);

		return character;
	}

	/**
	 * 캐릭터 타입 변경 시
	 */
	@Transactional
	public CharacterDto updateCharacter(Long groupId, TypeUpdateDto typeUpdateDto) {

		Set<CharacterDto> characterSet = getCharacterSet(groupId);

		CharacterDto character = characterSet.stream()
			.filter(c -> c.getUserId().equals(typeUpdateDto.getUserId()))
			.findAny()
			.orElseThrow();

		updateType(character, typeUpdateDto);

		setCharacterSet(groupId, characterSet);

		character.setEventType(EventType.TYPE_UPDATE);

		return character;
	}

	/**
	 * {@link CharacterDto}의 Location, Rotation 정보를 업데이트 하는 메서드
	 */
	private void updateLocation(CharacterDto characterDto, MoveDto moveDto) {

		characterDto.setLocation(
			CharacterLocation.builder().x(moveDto.getX()).y(moveDto.getY()).z(moveDto.getZ()).build());

		characterDto.setRotation(
			CharacterRotation.builder().rx(moveDto.getRx()).ry(moveDto.getRy()).rz(moveDto.getRz()).build());
	}

	/**
	 * {@link CharacterDto}의 Type 정보를 업데이트 하는 메서드
	 */
	private void updateType(CharacterDto characterDto, TypeUpdateDto typeUpdateDto) {

		characterDto.setType(CharacterType.builder()
			.color(typeUpdateDto.getColor())
			.meshType(typeUpdateDto.getMeshType())
			.faceType(typeUpdateDto.getFaceType())
			.build());
	}

	/**
	 * GroupId에 해당하는 redis에 characterSet을 저장하는 메서드
	 */
	private void setCharacterSet(Long groupId, Set<CharacterDto> characterSet) {
		redisTemplate.opsForValue().set(getGroupKey(groupId), characterSet);
	}

	/**
	 * GroupId에 해당하는 characterSet을 redis에서 불러오는 메서드
	 */
	public Set<CharacterDto> getCharacterSet(Long groupId) {

		Set<CharacterDto> characterSet = redisTemplate.opsForValue().get(getGroupKey(groupId));

		return characterSet == null ? new HashSet<>() : characterSet;
	}

	/**
	 * 캐릭터 Entity를 통해 Type 객체를 얻는 메서드
	 */
	private CharacterType getType(UserCharacter userCharacter) {
		return CharacterType.builder()
			.color(userCharacter.getColor())
			.meshType(userCharacter.getMeshType())
			.faceType(userCharacter.getFaceType())
			.build();
	}

	/**
	 * 기본 위치를 지정하는 메서드
	 */
	private CharacterLocation getDefaultLocation() {
		return CharacterLocation.builder().x(0.0).y(0.0).z(0.0).build();
	}

	/**
	 * 기본 rotation을 지정하는 메서드
	 */
	private CharacterRotation getDefaultRotation() {
		return CharacterRotation.builder().rx(0.0).ry(0.0).rz(0.0).build();
	}

	/**
	 * 그룹 ID에 해당하는 redis key 값을 생성하는 메서드
	 */
	private String getGroupKey(Long groupId) {
		return "Group" + groupId;
	}
}
