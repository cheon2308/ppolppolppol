package com.ppol.search.dto.response;

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
public class CommentDto {

	private Long commentId;

	private Long articleId;

	private Long parent;

	private CommentDto comment;

	private ArticleUserDto writer;

	private LocalDateTime createdAt;

	private String createString;

	private boolean isLike;
}
