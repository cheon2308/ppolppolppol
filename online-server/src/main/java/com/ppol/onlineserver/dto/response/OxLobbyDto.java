package com.ppol.onlineserver.dto.response;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * ox 게임 정보를 담는 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OxLobbyDto {

	private Long userId;
	private int problemNum;
	private int problemSec;
	private Set<String> oxPlayers;
}
