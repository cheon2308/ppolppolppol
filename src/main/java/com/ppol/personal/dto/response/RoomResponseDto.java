package com.ppol.personal.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.ppol.personal.entity.personal.PersonalRoom;
import com.ppol.personal.util.DateTimeUtils;
import com.ppol.personal.util.constatnt.enums.OpenStatus;
import com.ppol.personal.util.constatnt.enums.RoomMap;

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

	private RoomMap roomType;

	private int isMyRoom;

	private UserCharacterDto roomOwner;

	private UserCharacterDto player;

	private List<UserCharacterDto> npc;

	private List<AlbumListDto> albums;
}