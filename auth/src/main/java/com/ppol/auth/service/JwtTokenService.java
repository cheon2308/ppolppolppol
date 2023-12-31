package com.ppol.auth.service;

import org.springframework.stereotype.Service;

import com.ppol.auth.dto.response.JwtToken;
import com.ppol.auth.exception.exception.ForbiddenException;
import com.ppol.auth.exception.exception.TokenExpiredException;
import com.ppol.auth.repository.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtTokenService {

	private final RefreshTokenRepository refreshRepository;
	private final JwtTokenProviderService jwtTokenProviderService;

	public Long getUserId(String accessToken) {
		return Long.parseLong(jwtTokenProviderService.getUid(accessToken));
	}

	public String refreshToken(String refreshToken) {

		validRefreshToken(refreshToken);

		String id = jwtTokenProviderService.getUid(refreshToken);
		JwtToken newToken = jwtTokenProviderService.generateToken(id, refreshToken);

		return newToken.getAccessToken();
	}

	private void validRefreshToken(String refresh) {
		if (refresh == null || jwtTokenProviderService.verifyToken(refresh) || !refreshRepository.existsByRefreshToken(
			refresh)) {
			throw new TokenExpiredException("REFRESH TOKEN");
		}
	}
}