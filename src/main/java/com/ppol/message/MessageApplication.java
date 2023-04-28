package com.ppol.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableCaching
@EnableJpaAuditing
@EnableDiscoveryClient // Eureka Server에 등록
@SpringBootApplication
public class MessageApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessageApplication.class, args);
	}

}
