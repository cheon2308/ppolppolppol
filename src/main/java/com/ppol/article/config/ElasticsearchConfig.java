package com.ppol.article.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import lombok.NonNull;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.ppol.article.repository.elasticsearch")
// 이후 ElasticSearch repository 폴더 경로 넣을 것
public class ElasticsearchConfig extends ElasticsearchConfiguration {

	@Value("${elasticsearch.host}")
	private String host;

	@NonNull
	@Override
	public ClientConfiguration clientConfiguration() {
		return ClientConfiguration.builder().connectedTo(host).build();
	}

}
