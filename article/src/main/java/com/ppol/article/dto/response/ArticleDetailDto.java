package com.ppol.article.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.ppol.article.util.constatnt.enums.OpenStatus;

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

	private String content;

	private List<String> imageList;

	private UserResponseDto writer;

	private int likeCount;

	private OpenStatus openStatus;

	private LocalDateTime createdAt;

	private String createString;

	private UserInteraction userInteraction;
}
