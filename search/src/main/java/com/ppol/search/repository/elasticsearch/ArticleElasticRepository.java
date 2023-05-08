package com.ppol.search.repository.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.ppol.search.document.Article;

public interface ArticleElasticRepository extends ElasticsearchRepository<Article, Long> {
}
