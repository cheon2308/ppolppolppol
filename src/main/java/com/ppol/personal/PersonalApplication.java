package com.ppol.personal;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
<<<<<<< HEAD
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
=======
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
>>>>>>> 31f4840b4d9fc761544fa51c91680f9616918e4a

import jakarta.annotation.PostConstruct;

@EnableJpaAuditing
@EnableAsync
@SpringBootApplication
@EnableDiscoveryClient
public class PersonalApplication {

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
		SpringApplication.run(PersonalApplication.class, args);
	}

}
