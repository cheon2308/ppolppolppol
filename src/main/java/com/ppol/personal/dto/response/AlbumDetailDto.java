package com.ppol.personal.dto.response;

import java.time.LocalDateTime;

import com.ppol.personal.entity.personal.Album;
import com.ppol.personal.util.DateTimeUtils;
import com.ppol.personal.util.constatnt.enums.OpenStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 앨범의 상세 정보를 담아서 반환하기 위한 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AlbumDetailDto {

	private Long albumId;

	private Long roomId;

	private String title;

	private UserDto owner;

	private OpenStatus openStatus;

	private LocalDateTime createdAt;

	private String createString;

	public static AlbumDetailDto of(Album album) {
		return AlbumDetailDto.builder()
			.albumId(album.getId())
			.roomId(album.getPersonalRoom().getId())
			.title(album.getTitle())
			.owner(UserDto.of(album.getOwner()))
			.openStatus(album.getOpenStatus())
			.createdAt(album.getCreatedAt())
			.createString(DateTimeUtils.getString(album.getCreatedAt()))
			.build();
	}
}
