package com.ppol.group.service.article;

import org.springframework.stereotype.Service;

import com.ppol.group.dto.request.article.ArticleUpdateDto;
import com.ppol.group.dto.response.article.ArticleDetailDto;
import com.ppol.group.entity.group.GroupArticle;
import com.ppol.group.exception.exception.ForbiddenException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 그룹 게시글/피드를 수정하는 기능들을 제공하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleUpdateService {

	// service
	private final ArticleReadService articleReadService;

	/**
	 * 게시글/피드 내용을 수정하는 메서드
	 */
	@Transactional
	public ArticleDetailDto updateArticle(Long groupId, Long articleId, Long userId,
		ArticleUpdateDto articleUpdateDto) {

		GroupArticle article = articleReadService.getArticleOnlyWriter(articleId, groupId, userId);

		article.updateContent(articleUpdateDto.getContent());

		return ArticleDetailDto.of(article);
	}

	/**
	 * 게시글/피드의 상단 고정 여부를 변경하는 메서드
	 */
	@Transactional
	public ArticleDetailDto updateArticleFixed(Long groupId, Long articleId, Long userId) {

		GroupArticle article = articleReadService.getArticle(articleId, groupId, userId);

		if (!article.getGroup().getOwner().getId().equals(userId)) {
			throw new ForbiddenException("게시글/피드 상단 고정");
		}

		article.updateFixed();

		return ArticleDetailDto.of(article);
	}
}
