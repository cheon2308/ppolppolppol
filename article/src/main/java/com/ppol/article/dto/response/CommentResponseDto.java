package com.ppol.article.dto.response;

import java.time.LocalDateTime;

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

	private Long parent;

	private String content;

	private CommentResponseDto comment;

	private UserResponseDto writer;

	private LocalDateTime createdAt;

	private String createString;

	private boolean isLike;
}
