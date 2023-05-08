package com.ppol.personal.dto.request;

import com.ppol.personal.util.constatnt.enums.OpenStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 개인 룸의 기본 정보를 업데이트 하기 위한 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RoomUpdateDto {

	private OpenStatus openStatus;
	private String quiz;
	private String answer;
}
