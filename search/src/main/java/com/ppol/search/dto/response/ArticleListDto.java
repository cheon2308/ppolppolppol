package com.ppol.search.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.ppol.search.util.constatnt.enums.OpenStatus;

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
public class ArticleListDto {

	private Long articleId;

	private List<String> imageList;

	private String content;

	private CommentDto comment;

	private ArticleUserDto writer;

	private OpenStatus openStatus;

	private LocalDateTime createdAt;

	private String createString;

	private UserInteraction userInteraction;
}
