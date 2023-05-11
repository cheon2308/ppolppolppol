package com.ppol.onlineserver.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 사용자의 이동 정보를 담는 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MoveDto {

	private Long userId;
	private Double x;
	private Double y;
	private Double z;
	private Double rx;
	private Double ry;
	private Double rz;
}
