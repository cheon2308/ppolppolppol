package com.ppol.search.repository.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.ppol.search.document.User;

public interface UserElasticRepository extends ElasticsearchRepository<User, Long> {
}
