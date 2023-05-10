package com.ppol.personal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PersonalApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalApplication.class, args);
	}

}
