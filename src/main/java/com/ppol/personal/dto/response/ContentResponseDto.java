package com.ppol.personal.dto.response;

import java.time.LocalDateTime;

import com.ppol.personal.entity.personal.AlbumContent;
import com.ppol.personal.util.DateTimeUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 앨범의 각 콘텐츠 정보를 담아서 반환하기 위한 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ContentResponseDto {

	private Long albumContentId;

	private Long albumId;

	private String content;

	private String image;

	private LocalDateTime createdAt;

	private String createString;

	public static ContentResponseDto of(AlbumContent albumContent) {
		return ContentResponseDto.builder()
			.albumContentId(albumContent.getId())
			.albumId(albumContent.getAlbum().getId())
			.content(albumContent.getContent() == null ? "" : albumContent.getContent())
			.image(albumContent.getImage())
			.createdAt(albumContent.getCreatedAt())
			.createString(DateTimeUtils.getString(albumContent.getCreatedAt()))
			.build();
	}
}
