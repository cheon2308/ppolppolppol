package com.ppol.personal.dto.response;

import java.time.LocalDateTime;

import com.ppol.personal.entity.personal.AlbumComment;
import com.ppol.personal.util.DateTimeUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 앨범의 댓글 정보를 담아서 반환하기 위한 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CommentResponseDto {

	private Long albumCommentId;

	private Long albumId;

	private UserDto writer;

	private String content;

	private LocalDateTime createdAt;

	private String createString;

	public static CommentResponseDto of(AlbumComment comment) {
		return CommentResponseDto.builder()
			.albumCommentId(comment.getId())
			.albumId(comment.getAlbum().getId())
			.writer(UserDto.of(comment.getWriter()))
			.content(comment.getContent())
			.createdAt(comment.getCreatedAt())
			.createString(DateTimeUtils.getString(comment.getCreatedAt()))
			.build();
	}
}
