package com.ppol.group.dto.request.article;

import com.ppol.group.util.constatnt.classes.ValidationConstants;

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
public class ArticleUpdateDto {

	@NotNull
	@Size(max = ValidationConstants.ARTICLE_CONTENT_MAX_SIZE)
	private String content;
}
