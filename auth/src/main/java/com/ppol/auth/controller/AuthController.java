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

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final JwtTokenService jwtTokenService;

	@GetMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestHeader(name = "RefreshToken") String refreshToken) {

		String returnObject = jwtTokenService.refreshToken(refreshToken);

		return ResponseBuilder.ok(returnObject);
	}

	@PostMapping("/token")
	public Long accessToken(@RequestBody String accessToken) {

		//return jwtTokenService.getUserId(accessToken);
		return 1L;
	}
}
