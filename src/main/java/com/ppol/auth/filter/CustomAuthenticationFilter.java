package com.ppol.auth.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.ppol.auth.dto.request.AuthDto;
import com.ppol.auth.dto.response.JwtToken;
import com.ppol.auth.entity.RefreshToken;
import com.ppol.auth.entity.User;
import com.ppol.auth.exception.exception.BadRequestException;
import com.ppol.auth.repository.RefreshTokenRepository;
import com.ppol.auth.security.CustomAuthenticationToken;
import com.ppol.auth.security.CustomUserDetails;
import com.ppol.auth.service.JwtTokenProviderService;
import com.ppol.auth.service.JwtTokenService;
import com.ppol.auth.util.response.Response;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final JwtTokenProviderService jwtTokenProviderService;
	private final JwtTokenService jwtTokenService;
	private final RefreshTokenRepository refreshTokenRepository;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException {

		ObjectMapper om = new ObjectMapper();

		AuthDto authDto;

		try {
			authDto = om.readValue(request.getInputStream(), AuthDto.class);
		} catch (IOException e) {
			throw new BadRequestException("로그인");
		}

		log.info("{}", authDto);

		CustomAuthenticationToken authenticationToken = new CustomAuthenticationToken(authDto.getAccountId(),
			authDto.getPassword(), authDto.getProvider());

		return authenticationManager.authenticate(authenticationToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) {

		CustomUserDetails userDetails = (CustomUserDetails)authResult.getPrincipal();

		//token 생성
		JwtToken jwtToken = jwtTokenProviderService.generateToken(userDetails.getUser().getId().toString(), null);

		saveRefresh(jwtToken.getRefreshToken(), userDetails.getUser());

		// TODO refresh 토큰 새로 생성해서 저장하고 클라이언트로 토큰 두개 보내는거

		jwtTokenService.saveRefreshToken(response, jwtToken.getRefreshToken());

		AccessToken accessToken = AccessToken.of(jwtToken);

		// 커스텀 Response 객체 생성
		Response<String> responseBody = Response.of(accessToken.getAccessToken(), 200, "Success");

		// 응답 데이터를 JSON으로 변환
		String jsonResponse = JsonParser.toJson(responseBody);

		// JSON 응답 작성
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
		response.setStatus(HttpServletResponse.SC_OK);

		try {
			response.getWriter().write(jsonResponse);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void saveRefresh(String refreshToken, User user) {

		RefreshToken refresh = refreshTokenRepository.findByUser(user)
			.orElseGet(() -> RefreshToken.builder().user(user).refreshToken(refreshToken).build());

		refresh.updateRefresh(refreshToken);

		refreshTokenRepository.save(refresh);
	}
}