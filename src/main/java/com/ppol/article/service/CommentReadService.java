package com.ppol.article.service;

import org.springframework.stereotype.Service;

import com.ppol.article.dto.response.CommentDto;
import com.ppol.article.dto.response.UserDto;
import com.ppol.article.entity.article.ArticleComment;
import com.ppol.article.repository.jpa.ArticleCommentRepository;
import com.ppol.article.util.DateTimeUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentReadService {

	// repositories
	private final ArticleCommentRepository commentRepository;

	// services

	// others

	public CommentDto getPresentComment(Long articleId) {

		ArticleComment comment = commentRepository.findTop1ByArticle_IdOrderByLikeCount(articleId).orElse(null);

		return comment == null ? null : presentCommentMapping(comment);
	}

	private CommentDto presentCommentMapping(ArticleComment comment) {

		return CommentDto.builder()
			.commentId(comment.getId())
			.articleId(comment.getArticle().getId())
			.writer(UserDto.of(comment.getWriter()))
			.createString(DateTimeUtils.getString(comment.getCreatedAt()))
			.createdAt(comment.getCreatedAt())
			.build();
	}
}
