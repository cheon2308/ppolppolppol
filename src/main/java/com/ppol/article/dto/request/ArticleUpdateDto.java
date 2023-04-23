package com.ppol.article.dto.request;

import java.util.List;

import com.ppol.article.util.constatnt.enums.OpenStatus;

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

	@NotNull(message = "게시글 내용을 입력해주세요.")
	@Size(min = 1, max = ArticleConstants.MAX_ARTICLE_SIZE, message = "게시글 내용은 최대 "
		+ ArticleConstants.MAX_ARTICLE_SIZE + "자 입니다.")
	private String content;

	@NotNull(message = "게시글 공개 여부를 선택해주세요.")
	private OpenStatus openStatus;

	private List<String> hashTags;

}
