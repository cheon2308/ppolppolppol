package com.ppol.onlineserver;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import jakarta.annotation.PostConstruct;

@EnableAsync
@EnableJpaAuditing
@SpringBootApplication
@EnableDiscoveryClient
public class OnlineServerApplication {

	// JVM 시간대를 한국 시간으로 설정 !
	@PostConstruct
	public void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}

	public static void main(String[] args) {
		SpringApplication.run(OnlineServerApplication.class, args);
	}
}
