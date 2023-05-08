package com.ppol.article.repository.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.ppol.article.document.elastic.Article;

public interface ArticleElasticRepository extends ElasticsearchRepository<Article, Long> {
}
