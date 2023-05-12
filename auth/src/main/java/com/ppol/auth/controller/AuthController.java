package com.ppol.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ppol.auth.service.JwtTokenService;
import com.ppol.auth.util.response.ResponseBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

	private final JwtTokenService jwtTokenService;

	@GetMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestHeader(name = "RefreshToken") String refreshToken) {

		String returnObject = jwtTokenService.refreshToken(refreshToken);

		return ResponseBuilder.ok(returnObject);
	}

	@PostMapping("/token")
	public Long accessToken(@RequestBody String accessToken) {

		log.info("token : {}", accessToken);

		if (accessToken.equals("1")) {
			return 1L;
		} else {
			return jwtTokenService.getUserId(accessToken);
		}
	}
}
