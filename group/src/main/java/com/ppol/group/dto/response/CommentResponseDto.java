package com.ppol.group.dto.response;

import java.time.LocalDateTime;

import com.ppol.group.entity.group.GroupArticleComment;
import com.ppol.group.util.DateTimeUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CommentResponseDto {

	private Long commentId;

	private Long articleId;

	private String content;

	private UserResponseDto writer;

	private LocalDateTime createdAt;

	private String createString;

	public static CommentResponseDto of(GroupArticleComment comment) {
		return CommentResponseDto.builder()
			.commentId(comment.getId())
			.articleId(comment.getGroupArticle().getId())
			.content(comment.getContent())
			.writer(UserResponseDto.of(comment.getWriter()))
			.createdAt(comment.getCreatedAt())
			.createString(DateTimeUtils.getString(comment.getCreatedAt()))
			.build();
	}
}
