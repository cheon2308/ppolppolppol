package com.ppol.auth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ppol.auth.dto.response.JwtToken;
import com.ppol.auth.exception.exception.ForbiddenException;
import com.ppol.auth.repository.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtTokenService {

	private final RefreshTokenRepository refreshRepository;
	private final JwtTokenProviderService jwtTokenProviderService;

	@Value("${token.domain}")
	private String tokenDomain;

	public String refreshToken(String refreshToken) {

		validRefreshToken(refreshToken);

		String id = jwtTokenProviderService.getUid(refreshToken);
		JwtToken newToken = jwtTokenProviderService.generateToken(id, refreshToken);

		return newToken.getAccessToken();
	}

	private void validRefreshToken(String refresh) {
		if (refresh == null || jwtTokenProviderService.verifyToken(refresh) || !refreshRepository.existsByRefreshToken(
			refresh)) {
			// TODO valid 예외 처리
			throw new ForbiddenException("REFRESH EXPIRED");
		}
	}
}