package com.ppol.article.dto.request;

import com.ppol.article.util.constatnt.classes.ValidationConstants;
import com.ppol.article.util.constatnt.classes.ValidationMessages;

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
public class CommentCreateDto {

	@NotNull(message = "댓글 내용을 입력해주세요.")
	@Size(min = 1, max = ValidationConstants.COMMENT_CONTENT_MAX_SIZE, message = ValidationMessages.ARTICLE_SIZE_MSG)
	private String content;

	private Long parent;
}
