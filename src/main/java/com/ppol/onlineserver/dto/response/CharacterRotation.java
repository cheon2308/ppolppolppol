package com.ppol.onlineserver.dto.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 방향 정보
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CharacterRotation implements Serializable {

	private Double rx;
	private Double ry;
	private Double rz;
}
