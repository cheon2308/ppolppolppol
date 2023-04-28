package com.ppol.search.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import lombok.NonNull;

/**
 * 	엘라스틱서치 Config Class
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.ppol.search.repository.elasticsearch")
public class ElasticsearchConfig extends ElasticsearchConfiguration {

	@Value("${elasticsearch.host}")
	private String host;

	@NonNull
	@Override
	public ClientConfiguration clientConfiguration() {
		return ClientConfiguration.builder().connectedTo(host).build();
	}

}
