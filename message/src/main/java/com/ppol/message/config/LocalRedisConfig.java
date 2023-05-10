package com.ppol.message.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import redis.embedded.RedisServer;

@Profile("local")
@Configuration
@Slf4j
public class LocalRedisConfig {
	@Value("${spring.data.redis.port}")
	private int redisPort;

	private RedisServer redisServer;

	@PostConstruct
	public void redisServer() {
		redisServer = RedisServer.builder()
			.port(redisPort)
			.setting("maxheap 128M")
			.build();
		try {
			redisServer.start();
		} catch (Exception e) {
			log.error("", e);
		}
	}

	@PreDestroy
	public void stopRedis() {
		if (redisServer != null) {
			redisServer.stop();
		}
	}
}
