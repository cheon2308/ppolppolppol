package com.ppol.article.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CommentUpdateDto {

	@NotNull(message = "댓글 내용을 입력해주세요.")
	@Size(min = 1, max = ArticleConstants.MAX_COMMENT_SIZE, message = "댓글 내용은 최대 "
		+ ArticleConstants.MAX_COMMENT_SIZE + "자 입니다.")
	private String content;
}
