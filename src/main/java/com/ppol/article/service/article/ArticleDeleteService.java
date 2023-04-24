package com.ppol.article.service.article;

import org.springframework.stereotype.Service;

import com.ppol.article.entity.article.Article;
import com.ppol.article.service.other.ArticleElasticService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 게시글 삭제 기능을 담당하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleDeleteService {

	// services
	private final ArticleReadService articleReadService;
	private final ArticleElasticService articleElasticService;

	/**
	 * 게시글/피드 아이디값를 통해 MariaDB와 Elasticsearch에 저장된 게시글 정보를 삭제한다.
	 */
	@Transactional
	public void articleDelete(Long articleId, Long userId) {
		Article article = articleReadService.getArticleWithAuth(articleId, userId);

		article.delete();

		articleElasticService.deleteArticleElasticsearch(articleId);
	}
}
