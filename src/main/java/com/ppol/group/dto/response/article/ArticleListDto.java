package com.ppol.group.dto.response.article;

import java.time.LocalDateTime;
import java.util.List;

import com.ppol.group.dto.response.CommentResponseDto;
import com.ppol.group.dto.response.UserResponseDto;

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

	private Long groupId;

	private boolean fixed;

	private List<String> imageList;

	private String content;

	private CommentResponseDto comment;

	private UserResponseDto writer;

	private LocalDateTime createdAt;

	private String createString;
}
