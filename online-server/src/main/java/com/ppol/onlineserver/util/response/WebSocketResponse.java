package com.ppol.onlineserver.util.response;

import com.ppol.onlineserver.util.constant.enums.EventType;

import lombok.Getter;
import lombok.ToString;

/**
 * 사용자의 캐릭터 정보를 담는 DTO
 */
@Getter
@ToString
public class WebSocketResponse<T> {

	// 사용자 ID
	private final Long userId;

	private final String username;

	private final T data;

	// 이벤트 정보
	private final EventType eventType;

	private WebSocketResponse(Long userId, String username, T data, EventType eventType) {
		this.userId = userId;
		this.username = username;
		this.data = data;
		this.eventType = eventType;
	}

	public static <T> WebSocketResponse<?> of(Long userId, String username, T data, EventType eventType) {
		return new WebSocketResponse<>(userId, username, data, eventType);
	}
}
