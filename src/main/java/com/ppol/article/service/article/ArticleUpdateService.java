package com.ppol.article.service.article;

import org.springframework.stereotype.Service;

import com.ppol.article.dto.request.ArticleUpdateDto;
import com.ppol.article.dto.response.ArticleDetailDto;
import com.ppol.article.entity.article.Article;
import com.ppol.article.service.other.ArticleElasticService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 게시글 수정 기능을 담당하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleUpdateService {

	// services
	private final ArticleReadService articleReadService;
	private final ArticleElasticService articleElasticService;

	/**
	 * 게시글/피드 아이디값과 {@link ArticleUpdateDto} 정보를 통해 MariaDB와 Elasticsearch에 저장된 정보를 수정한다.
	 */
	@Transactional
	public ArticleDetailDto articleUpdate(ArticleUpdateDto articleUpdateDto, Long articleId, Long userId) {

		Article article = articleReadService.getArticleWithAuth(articleId, userId);

		article.updateContent(articleUpdateDto.getContent());
		article.updateOpenStatus(articleUpdateDto.getOpenStatus());

		articleElasticService.updateArticleElasticsearch(articleUpdateDto, articleId);

		return articleReadService.articleDetailMapping(article, userId);
	}
}
