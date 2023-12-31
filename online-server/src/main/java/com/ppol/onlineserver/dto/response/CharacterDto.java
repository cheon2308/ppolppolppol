package com.ppol.onlineserver.dto.response;

import java.io.Serializable;

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
public class CharacterDto implements Serializable {

	// 사용자 ID
	@NotNull
	private Long userId;

	private String username;

	// 캐릭터 구성 타입
	private CharacterType type;

	// 위치 정보
	private CharacterLocation location;

	// 방향 정보
	private CharacterRotation rotation;
}
