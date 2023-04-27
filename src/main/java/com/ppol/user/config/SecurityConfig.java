package com.ppol.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	// FilterChain
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		return http.httpBasic()
			.disable()
			.csrf()
			.disable()
			.formLogin()
			.disable()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeHttpRequests()
			.anyRequest()
			.permitAll()
			.and()
			.build();
	}
}
