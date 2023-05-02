package com.ppol.group.dto.request.article;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

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
public class ArticleCreateDto {

	@NotNull(message = "게시글 내용을 입력해주세요.")
	@Size(min = 1, max = ValidationConstants.ARTICLE_CONTENT_MAX_SIZE)
	private String content;

	private List<MultipartFile> imageList;
}
