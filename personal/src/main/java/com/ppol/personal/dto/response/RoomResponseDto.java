package com.ppol.personal.dto.response;

import java.time.LocalDateTime;

import com.ppol.personal.entity.personal.PersonalRoom;
import com.ppol.personal.util.DateTimeUtils;
import com.ppol.personal.util.constatnt.enums.OpenStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 개인 방의 정보를 담아서 반환하기 위한 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RoomResponseDto {

	private Long roomId;

	private String title;

	private UserDto owner;

	private OpenStatus openStatus;

	private String quiz;

	private LocalDateTime createdAt;

	private String createString;

	public static RoomResponseDto of(PersonalRoom personalRoom) {
		return RoomResponseDto.builder()
			.roomId(personalRoom.getId())
			.title(personalRoom.getOwner().getUsername() + "님의 공간")
			.owner(UserDto.of(personalRoom.getOwner()))
			.openStatus(personalRoom.getOpenStatus())
			.quiz(personalRoom.getQuiz())
			.createdAt(personalRoom.getCreatedAt())
			.createString(DateTimeUtils.getString(personalRoom.getCreatedAt()))
			.build();
	}
}