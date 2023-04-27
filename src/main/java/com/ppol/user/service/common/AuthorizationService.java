package com.ppol.user.service.common;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * 	JWT 토큰 인증 기능을 위한 서비스
 */
@Service
@Slf4j
public class AuthorizationService {

	// TODO 인증 서버로 부터 JWT 토큰을 통해 userId를 받아오는 로직 구현
	public Long getUserId(String accessToken) {
		return 1L;
	}
}
