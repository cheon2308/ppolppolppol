package com.ppol.message.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 	기본 설정 및 Bean들을 등록하기 위한 Config Class
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	/**
	 *	Cors 설정
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOriginPatterns("*")
			.allowedMethods("*")
			.allowCredentials(true)
			.maxAge(3000);
	}
}
