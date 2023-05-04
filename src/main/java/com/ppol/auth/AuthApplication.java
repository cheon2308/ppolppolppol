package com.ppol.auth;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableJpaAuditing
@EnableDiscoveryClient
public class AuthApplication {

	// S3 관련 설정 인듯?
	static {
		System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
	}

	// JVM 시간대를 한국 시간으로 설정 !
	@PostConstruct
	public void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

}
