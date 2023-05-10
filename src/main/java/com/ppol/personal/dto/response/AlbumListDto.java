package com.ppol.personal.dto.response;

import java.time.LocalDateTime;

import com.ppol.personal.entity.personal.Album;
import com.ppol.personal.util.DateTimeUtils;
import com.ppol.personal.util.constatnt.classes.DateTimeFormatString;
import com.ppol.personal.util.constatnt.enums.AlbumColor;
import com.ppol.personal.util.constatnt.enums.OpenStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 앨범을 목록 형태로 정보를 담아서 반환하기 위한 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AlbumListDto {

	private Long albumId;

	private String title;

	private AlbumColor color;

	private OpenStatus openStatus;

	private String quiz;

	public static AlbumListDto of(Album album) {
		return AlbumListDto.builder()
			.albumId(album.getId())
			.title(album.getTitle())
			.color(album.getColor())
			.openStatus(album.getOpenStatus())
			.quiz(album.getQuiz())
			.build();
	}
}
