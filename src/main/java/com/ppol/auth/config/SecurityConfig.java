package com.ppol.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.ppol.auth.filter.CustomAuthenticationFilter;
import com.ppol.auth.repository.RefreshTokenRepository;
import com.ppol.auth.service.JwtTokenProviderService;
import com.ppol.auth.service.JwtTokenService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final JwtTokenProviderService jwtTokenProviderService;
	private final JwtTokenService jwtTokenService;
	private final RefreshTokenRepository refreshTokenRepository;

	//PasswordEncoder
	@Bean
	public BCryptPasswordEncoder encodePassword() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
		return authConfiguration.getAuthenticationManager();
	}

	//CorsFilter
	@Bean
	public CorsFilter corsFilter() {

		CorsConfiguration config = new CorsConfiguration();

		config.setAllowCredentials(true); //내서버가 응답을 할때 json 을 자바스크립트에서 처리할 수 있게 할지
		config.addAllowedOriginPattern("*"); //모든 아이피를 응답허용
		config.addAllowedHeader("*"); //모든 header 응답허용
		config.addAllowedMethod("*"); //모든 post,get,put 허용

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		return new CorsFilter(source);
	}

	// FilterChain
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, CorsFilter corsFilter,
		AuthenticationManager authenticationManager) throws Exception {

		return http.httpBasic()
			.disable()
			.csrf()
			.disable()
			.formLogin().disable()
			.addFilter(corsFilter)
			.addFilter(new CustomAuthenticationFilter(authenticationManager, jwtTokenProviderService, jwtTokenService,
				refreshTokenRepository))
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
