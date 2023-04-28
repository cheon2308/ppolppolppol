package com.ppol.search.service.article;

import org.springframework.stereotype.Service;

import com.ppol.search.dto.response.CommentDto;
import com.ppol.search.dto.response.ArticleUserDto;
import com.ppol.search.entity.article.ArticleComment;
import com.ppol.search.repository.jpa.ArticleCommentRepository;
import com.ppol.search.util.DateTimeUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 댓글 불러오기 기능을 담당하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CommentReadService {

	// repositories
	private final ArticleCommentRepository commentRepository;

	// services
	private final UserInteractionReadService userInteractionReadService;

	/**
	 * 특정 게시글에 대해 대표 댓글을 불러오는 메서드
	 * 대표 댓글의 우선순위 : 1순위 게시글 작성자의 댓글 중 좋아요 순서, 2순위 좋아요 순서
	 */
	public CommentDto getArticlePresentComment(Long articleId, Long writerId, Long userId) {

		ArticleComment comment = commentRepository.findTopByArticle_IdAndWriter_IdOrderByLikeCountDesc(articleId,
			writerId).orElse(null);

		comment = comment != null ? comment :
			commentRepository.findTopByArticle_IdOrderByLikeCountDesc(articleId).orElse(null);

		return comment == null ? null : commentMapping(comment, userId);
	}

	/**
	 * 댓글에 엔티티를 DTO로 매핑하는 메서드
	 */
	public CommentDto commentMapping(ArticleComment comment, Long userId) {

		return CommentDto.builder()
			.commentId(comment.getId())
			.articleId(comment.getArticle().getId())
			.writer(ArticleUserDto.of(comment.getWriter()))
			.parent(comment.getParent())
			.isLike(userInteractionReadService.getArticleCommentLike(userId, comment.getId()))
			.createString(DateTimeUtils.getString(comment.getCreatedAt()))
			.createdAt(comment.getCreatedAt())
			.build();
	}
}
