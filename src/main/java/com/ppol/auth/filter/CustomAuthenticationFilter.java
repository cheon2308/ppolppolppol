package com.ppol.auth.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ppol.auth.dto.request.AuthDto;
import com.ppol.auth.dto.response.JwtToken;
import com.ppol.auth.entity.RefreshToken;
import com.ppol.auth.entity.User;
import com.ppol.auth.exception.exception.BadRequestException;
import com.ppol.auth.repository.RefreshTokenRepository;
import com.ppol.auth.security.CustomUserDetails;
import com.ppol.auth.service.JwtTokenProviderService;
import com.ppol.auth.util.constatnt.enums.Provider;
import com.ppol.auth.util.response.Response;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final JwtTokenProviderService jwtTokenProviderService;
	private final RefreshTokenRepository refreshTokenRepository;

	@Value("${social.password-key}")
	private String PASSWORD_KEY;

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

		String username = authDto.getProvider().getCode() + authDto.getAccountId();
		String password = authDto.getProvider().equals(Provider.EMAIL) ? authDto.getPassword() :
			authDto.getAccountId() + PASSWORD_KEY;

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
			password);

		return authenticationManager.authenticate(authenticationToken);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException failed) throws IOException, ServletException {

		failed.printStackTrace();

		super.unsuccessfulAuthentication(request, response, failed);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) {

		CustomUserDetails userDetails = (CustomUserDetails)authResult.getPrincipal();

		//token 생성
		JwtToken jwtToken = jwtTokenProviderService.generateToken(userDetails.user().getId().toString(), null);

		saveRefresh(jwtToken.getRefreshToken(), userDetails.user());

		// 커스텀 Response 객체 생성
		Response<?> responseBody = Response.ok(jwtToken);

		// 응답 데이터를 JSON으로 변환
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		String jsonResponse;
		try {
			jsonResponse = objectMapper.writeValueAsString(responseBody);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

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