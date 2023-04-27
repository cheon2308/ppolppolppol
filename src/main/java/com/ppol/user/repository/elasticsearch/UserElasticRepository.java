package com.ppol.user.repository.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.ppol.user.document.User;

public interface UserElasticRepository extends ElasticsearchRepository<User, Long> {
}
