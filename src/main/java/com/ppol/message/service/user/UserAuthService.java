package com.ppol.message.service.user;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAuthService {

	// TODO 토큰을 가지고 userId 불러오기 인증 서버로 부터
	public Long getUserId(String accessToken) {

		if(accessToken == null) {
			return 2L;
		}

		return 1L;
	}
}
