package com.ppol.article.dto.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

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
public class ArticleCreateDto {

	@NotNull(message = "게시글 내용을 입력해주세요.")
	@Size(min = 1, max = ValidationConstants.ARTICLE_CONTENT_MAX_SIZE, message = ValidationMessages.ARTICLE_SIZE_MSG)
	private String content;

	private List<MultipartFile> imageList;

	private String openStatus;

	private List<String> hashTags;
}
