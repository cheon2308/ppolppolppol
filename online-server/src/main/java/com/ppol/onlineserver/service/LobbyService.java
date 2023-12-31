package com.ppol.onlineserver.service;

import java.util.HashSet;

import org.springframework.stereotype.Service;

import com.ppol.onlineserver.dto.response.CharacterDto;
import com.ppol.onlineserver.dto.response.OxLobbyDto;
import com.ppol.onlineserver.entity.User;
import com.ppol.onlineserver.util.OxLobbyUtil;
import com.ppol.onlineserver.util.constant.enums.EventType;
import com.ppol.onlineserver.util.response.WebSocketResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 사용자가 로비에 참여하거나 나가거나 하는 행동들을 처리하기 위한 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LobbyService {

	// service
	private final CharacterReadService characterReadService;

	/**
	 * 새로운 게임을 만드는 로비 생성 메서드
	 */
	public WebSocketResponse<?> makeOxLobby(Long userId, String groupId, OxLobbyDto oxLobbyDto) {

		return getLobbyDto(userId, groupId, EventType.MAKE_LOBBY, oxLobbyDto);
	}

	/**
	 * 기존의 게임 취소하는 메서드
	 */
	public WebSocketResponse<?> destroyOxLobby(Long userId, String groupId) {

		User user = characterReadService.getUser(userId);

		if (OxLobbyUtil.delete(groupId)) {
			log.info("Delete {}", groupId);
		}

		return WebSocketResponse.of(user.getId(), user.getUsername(), null, EventType.DESTROY_LOBBY);
	}

	/**
	 * 사용자가 로비에 참여하는 DTO를 생성하는 메서드
	 */
	public WebSocketResponse<?> getEnterLobby(Long userId, String groupId) {
		return getLobbyDto(userId, groupId, EventType.ENTER_LOBBY, null);
	}

	/**
	 * 사용자가 로비에서 떠나는 DTO를 생성하는 메서드
	 */
	public WebSocketResponse<?> getLeaveLobby(Long userId, String groupId) {
		return getLobbyDto(userId, groupId, EventType.LEAVE_LOBBY, null);
	}

	/**
	 * 로비에 참여하거나 떠나는 {@link CharacterDto}를 만드는 메서드
	 */
	private WebSocketResponse<?> getLobbyDto(Long userId, String groupId, EventType eventType, OxLobbyDto oxLobbyDto) {
		User user = characterReadService.getUser(userId);

		oxLobbyDto = oxLobbyDto == null ? OxLobbyUtil.getOxLobby(groupId) : oxLobbyDto;

		if (oxLobbyDto.getOxPlayers() == null) {
			oxLobbyDto.setOxPlayers(new HashSet<>());
		}

		if (eventType == EventType.MAKE_LOBBY || eventType == EventType.ENTER_LOBBY) {
			oxLobbyDto.getOxPlayers().add(user.getUsername());
		} else if (eventType == EventType.LEAVE_LOBBY) {
			oxLobbyDto.getOxPlayers().remove(user.getUsername());
		}

		OxLobbyUtil.put(groupId, oxLobbyDto);

		return WebSocketResponse.of(user.getId(), user.getUsername(), oxLobbyDto, eventType);
	}
}
