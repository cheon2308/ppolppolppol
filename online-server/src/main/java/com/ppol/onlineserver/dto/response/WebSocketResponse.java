package com.ppol.onlineserver.dto.response;

import com.ppol.onlineserver.util.constant.enums.EventType;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 사용자의 캐릭터 정보를 담는 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class WebSocketResponse {

	// 사용자 ID
	@NotNull
	private Long userId;

	private String username;

	private CharacterDto character;

	private OxLobbyDto oxGame;

	// 이벤트 정보
	private EventType eventType;
}
