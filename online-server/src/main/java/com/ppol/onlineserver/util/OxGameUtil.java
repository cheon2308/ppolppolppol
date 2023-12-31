package com.ppol.onlineserver.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.ppol.onlineserver.dto.OxGameDto;
import com.ppol.onlineserver.dto.OxGameUserDto;
import com.ppol.onlineserver.dto.response.OxLobbyDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OxGameUtil {

	// redis
	private final RedisTemplate<String, OxGameDto> redisTemplate;

	private final Map<String, Set<OxGameUserDto>> oxUsers = new HashMap<>();

	private static final Set<String> gameKeySet = new HashSet<>();

	private static final String GAME_KEY_PREFIX = "OXG";
	private static final String GAME_ROOM_ID_PREFIX = "OX1025";

	/**
	 * Redis에 변경 내용 저장
	 */
	public void save(String gameRoomId, OxGameDto oxGameDto) {
		redisTemplate.opsForValue().set(getGameKey(gameRoomId), oxGameDto);
	}

	/**
	 * Redis에서 삭제
	 */
	public boolean delete(String gameRoomId) {
		return Boolean.TRUE.equals(redisTemplate.delete(getGameKey(gameRoomId)));
	}

	/**
	 * Redis에서 객체 불러오기
	 */
	public OxGameDto getOxGame(String gameRoomId) {
		OxGameDto oxGame = redisTemplate.opsForValue().get(getGameKey(gameRoomId));

		oxGame.setOxPlayers(oxUsers.get(gameRoomId));

		return oxGame;
	}

	/**
	 * 새로운 게임을 생성해서 Redis에 저장하고 랜덤하게 얻어온 게임 방의 id 반환
	 */
	public String makeNewGame(OxLobbyDto oxLobbyDto) {

		OxGameDto oxGame = OxGameDto.builder()
			.problemNum(oxLobbyDto.getProblemNum())
			.problemSec(oxLobbyDto.getProblemSec())
			.oxPlayers(new HashSet<>())
			.previousQuestions(new ArrayList<>())
			.playerCount(oxLobbyDto.getOxPlayers().size())
			.build();

		String gameRoomId = makeGameRoomId();

		oxUsers.put(gameRoomId, new HashSet<>());

		save(gameRoomId, oxGame);

		return gameRoomId;
	}

	/**
	 * 게임 종료 시 실행
	 */
	public void endGame(String gameRoomId) {

		delete(gameRoomId);
		oxUsers.remove(gameRoomId);
	}

	public int getPlayerCount(String gameRoomId) {
		return getOxGame(gameRoomId).getPlayerCount();
	}

	public void setPlayerCount(String gameRoomId, int count) {
		OxGameDto oxGameDto = getOxGame(gameRoomId);
		oxGameDto.setPlayerCount(count);

		save(gameRoomId, oxGameDto);
	}

	/**
	 * 특정 게임 객체에 사용자 추가
	 */
	public int addPlayer(String gameRoomId, OxGameUserDto user) {

		OxGameDto oxGameDto = getOxGame(gameRoomId);

		oxGameDto.getOxPlayers().add(user);

		save(gameRoomId, oxGameDto);

		oxUsers.get(gameRoomId).add(user);

		return oxGameDto.getOxPlayers().size();
	}

	/**
	 * 특정 게임 객체에 사용자 삭제
	 */
	public void removePlayer(String gameRoomId, Long userId) {

		OxGameDto oxGameDto = getOxGame(gameRoomId);

		oxGameDto.getOxPlayers().removeIf(user -> user.getUserId().equals(userId));

		save(gameRoomId, oxGameDto);

		OxGameUserDto oxGameUserDto = oxUsers.get(gameRoomId)
			.stream()
			.filter(user -> user.getUserId().equals(userId))
			.findAny()
			.orElseThrow();

		oxUsers.get(gameRoomId).remove(oxGameUserDto);
	}

	/**
	 * 문제 맞춘 개수 추가
	 */
	public void addPlayerCount(String gameRoomId, Long userId) {
		oxUsers.get(gameRoomId)
			.stream()
			.filter(user -> user.getUserId().equals(userId))
			.findAny()
			.orElseThrow()
			.addCount();
	}

	/**
	 * 무작위 id 값을 불러오기, 중복 체크 로직 포함
	 */
	private String makeGameRoomId() {

		String randomKey = GAME_ROOM_ID_PREFIX + UUID.randomUUID();

		while (gameKeySet.contains(randomKey)) {
			randomKey = GAME_ROOM_ID_PREFIX + UUID.randomUUID();
		}

		gameKeySet.add(randomKey);

		return randomKey;
	}

	private String getGameKey(String gameRoomId) {
		return GAME_KEY_PREFIX + gameRoomId;
	}
}
