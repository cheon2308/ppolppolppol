package com.ppol.group.dto.response.article;

import java.time.LocalDateTime;
import java.util.List;

import com.ppol.group.dto.response.UserResponseDto;
import com.ppol.group.entity.group.GroupArticle;
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
public class ArticleDetailDto {

	private Long articleId;

	private Long groupId;

	private boolean fixed;

	private List<String> imageList;

	private String content;

	private UserResponseDto writer;

	private LocalDateTime createdAt;

	private String createString;

	public static ArticleDetailDto of(GroupArticle article) {
		return ArticleDetailDto.builder()
			.articleId(article.getId())
			.groupId(article.getGroup().getId())
			.fixed(article.isFixed())
			.imageList(article.getImageList())
			.content(article.getContent())
			.writer(UserResponseDto.of(article.getWriter()))
			.createdAt(article.getCreatedAt())
			.createString(DateTimeUtils.getString(article.getCreatedAt()))
			.build();
	}
}
