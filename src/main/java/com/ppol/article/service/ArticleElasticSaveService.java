package com.ppol.article.service;

import org.springframework.stereotype.Service;

import com.ppol.article.document.elastic.Article;
import com.ppol.article.dto.request.ArticleCreateDto;
import com.ppol.article.repository.elasticsearch.ArticleElasticRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleElasticSaveService {

	// repositories
	private final ArticleElasticRepository articleElasticRepository;

	public void createArticleElasticsearch(ArticleCreateDto articleCreateDto, Long id) {

		Article article = Article.builder()
			.id(id)
			.content(articleCreateDto.getContent())
			.hashTags(articleCreateDto.getHashTags())
			.build();

		articleElasticRepository.save(article);
	}
}
