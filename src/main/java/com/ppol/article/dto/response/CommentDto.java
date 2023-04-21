package com.ppol.article.dto.response;

import java.time.LocalDateTime;
import java.util.List;

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

	private List<CommentDto> replyList;

	private UserDto writer;

	private LocalDateTime createdAt;

	private String createString;

	private boolean isLike;
}
