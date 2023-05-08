package com.ppol.search.service.article;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ppol.search.dto.response.ArticleListDto;
import com.ppol.search.dto.response.ArticleUserDto;
import com.ppol.search.entity.article.Article;
import com.ppol.search.repository.jpa.ArticleRepository;
import com.ppol.search.util.DateTimeUtils;

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
	 * article ID 값 리스트를 가지고 {@link ArticleListDto}를 리스트로 바꿔주는 메서드
	 */
	@Transactional
	public List<ArticleListDto> findArticleList(List<Long> articleIdList, Long userId) {

		return articleIdList.stream()
			.map(this::getArticle)
			.map(article -> articleListMapping(article, userId))
			.toList();
	}

	/**
	 * DB에서 id값으로 게시글을 찾고 없다면 예외처리
	 */
	public Article getArticle(Long articleId) {
		return articleRepository.findById(articleId).orElseThrow(() -> new EntityNotFoundException("게시글"));
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
			.writer(ArticleUserDto.of(article.getWriter()))
			.openStatus(article.getOpenStatus())
			.comment(commentReadService.getArticlePresentComment(article.getId(), article.getWriter().getId(), userId))
			.build();
	}
}
