package com.ppol.auth.service;

import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ppol.auth.dto.response.JwtToken;
import com.ppol.auth.exception.exception.TokenExpiredException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Service
public class JwtTokenProviderService {

	public static final Long TOKEN_PERIOD = 60L * 60 * 24 * 30 * 10;
	public static final Long REFRESH_PERIOD = 60L * 60 * 24 * 30 * 10;

	@Value("${jwt.secret-key}")
	private String secretKeyString;

	private SecretKey secretKey;

	@PostConstruct
	protected void init() {
		secretKeyString = Base64.getEncoder().encodeToString(secretKeyString.getBytes());
		secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes());
	}

	// Create a common JwtParser to avoid code duplication
	private JwtParser createJwtParser() {
		return Jwts.parserBuilder().setSigningKey(secretKey).build();
	}

	public JwtToken generateToken(String uid, String refreshToken) {
		Claims claims = Jwts.claims().setSubject(uid);

		Date now = new Date();

		// Extract common code for token and refreshToken creation
		String token = buildToken(claims, now, TOKEN_PERIOD * 1000L);
		String newRefreshToken = refreshToken != null ? refreshToken : buildToken(claims, now, REFRESH_PERIOD * 1000L);

		return new JwtToken(token, newRefreshToken);
	}

	private String buildToken(Claims claims, Date now, Long period) {
		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + period))
			.signWith(secretKey, SignatureAlgorithm.HS256)
			.compact();
	}

	public boolean verifyToken(String token) {
		Jws<Claims> claims = createJwtParser().parseClaimsJws(token);
		return !claims.getBody().getExpiration().after(new Date());
	}

	public String getUid(String token) {

		if (verifyToken(token)) {
			throw new TokenExpiredException("ACCESS TOKEN");
		}

		return createJwtParser().parseClaimsJws(token).getBody().getSubject();
	}
}
