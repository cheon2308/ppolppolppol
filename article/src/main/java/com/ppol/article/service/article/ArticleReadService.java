package com.ppol.article.service.article;

import java.time.LocalDateTime;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.ppol.article.dto.response.ArticleDetailDto;
import com.ppol.article.dto.response.ArticleListDto;
import com.ppol.article.dto.response.UserResponseDto;
import com.ppol.article.entity.article.Article;
import com.ppol.article.exception.exception.ForbiddenException;
import com.ppol.article.repository.jpa.ArticleRepository;
import com.ppol.article.service.comment.CommentReadService;
import com.ppol.article.service.user.UserInteractionReadService;
import com.ppol.article.util.DateTimeUtils;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 게시글 불러오기 기능을 담당하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleReadService {

	// repositories
	private final ArticleRepository articleRepository;

	// services
	private final UserInteractionReadService userInteractionReadService;
	private final CommentReadService commentReadService;

	/**
	 * 무한 스크롤 기능을 위해 timestamp 이전에 작성된 게시글들을 size 만큼 불러오는 메서드
	 */
	@Transactional
	public Slice<ArticleListDto> findArticleList(Long userId, Long lastArticleId, int size) {

		Article article = getArticlePermitNull(lastArticleId);

		LocalDateTime timestamp = article == null ? LocalDateTime.now() : article.getCreatedAt();
		lastArticleId = lastArticleId == null ? -1 : lastArticleId;

		Pageable pageable = PageRequest.of(0, size);

		Slice<Article> slice = articleRepository.findArticlesByFollowedUsers(userId, timestamp, lastArticleId,
			pageable);

		return slice.map(a -> articleListMapping(a, userId));
	}

	/**
	 * 하나의 게시글에 대한 정보를 불러오는 메서드
	 */
	@Transactional
	public ArticleDetailDto findArticle(Long articleId, Long userId) {

		Article article = getArticle(articleId);

		return articleDetailMapping(article, userId);
	}

	/**
	 * DB에서 id값으로 게시글을 찾고 없다면 예외처리
	 */
	public Article getArticle(Long articleId) {
		return articleRepository.findById(articleId).orElseThrow(() -> new EntityNotFoundException("게시글"));
	}

	/**
	 * articleId가 null인 경우 null을 리턴하고 아니면 엔티티를 찾는 메서드
	 */
	public Article getArticlePermitNull(Long articleId) {
		return articleId == null ? null : getArticle(articleId);
	}

	/**
	 * 게시글 수정, 삭제 시 게시글을 불러올 때 사용자의 수정, 삭제 권한이 있는지 확인하는 메서드
	 */
	public Article getArticleWithAuth(Long articleId, Long userId) {
		Article article = getArticle(articleId);

		if (!article.getWriter().getId().equals(userId)) {
			throw new ForbiddenException("게시글 수정/삭제");
		}

		return article;
	}

	/**
	 * 사용자 관련 정보를 포함한 게시글 상세정보 DTO를 생성하는 메서드
	 */
	public ArticleDetailDto articleDetailMapping(Article article, Long userId) {

		return ArticleDetailDto.builder()
			.articleId(article.getId())
			.content(article.getContent())
			.userInteraction(userInteractionReadService.getUserInteraction(userId, article))
			.createString(DateTimeUtils.getString(article.getCreatedAt()))
			.createdAt(article.getCreatedAt())
			.imageList(article.getImageList())
			.writer(UserResponseDto.of(article.getWriter()))
			.openStatus(article.getOpenStatus())
			.likeCount(article.getLikeCount())
			.build();
	}

	/**
	 * 사용자 관련 정보, 대표 댓글 정보를 포함한 게시글 목록정보 DTO를 생성하는 메서드
	 */
	public ArticleListDto articleListMapping(Article article, Long userId) {

		return ArticleListDto.builder()
			.articleId(article.getId())
			.content(article.getContent())
			.userInteraction(userInteractionReadService.getUserInteraction(userId, article))
			.createString(DateTimeUtils.getString(article.getCreatedAt()))
			.createdAt(article.getCreatedAt())
			.imageList(article.getImageList())
			.writer(UserResponseDto.of(article.getWriter()))
			.openStatus(article.getOpenStatus())
			.likeCount(article.getLikeCount())
			.comment(commentReadService.getArticlePresentComment(article.getId(), article.getWriter().getId(), userId))
			.build();
	}
}
