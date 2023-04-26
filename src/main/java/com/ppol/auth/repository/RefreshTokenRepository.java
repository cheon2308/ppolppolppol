package com.ppol.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ppol.auth.entity.RefreshToken;
import com.ppol.auth.entity.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

	Optional<RefreshToken> findByUser(User user);

	boolean existsByRefreshToken(String refreshToken);
}
