package com.ppol.article.service.article;

import org.springframework.stereotype.Service;

import com.ppol.article.repository.jpa.ArticleRepository;
import com.ppol.article.service.ArticleElasticService;

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

	// repositories
	private final ArticleRepository articleRepository;

	// services
	private final ArticleReadService articleReadService;
	private final ArticleElasticService articleElasticService;

	/**
	 * 게시글/피드 아이디값를 통해 MariaDB와 Elasticsearch에 저장된 게시글 정보를 삭제한다.
	 */
	@Transactional
	public void articleDelete(Long articleId, Long userId) {
		articleReadService.getArticleWithAuth(articleId, userId);

		articleRepository.deleteById(articleId);

		articleElasticService.deleteArticleElasticsearch(articleId);
	}
}
