package com.ppol.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ppol.auth.entity.User;
import com.ppol.auth.util.constatnt.enums.Provider;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByAccountId(String accountId);

	Optional<User> findByAccountIdAndPasswordAndProvider(String accountId, String password, Provider provider);
}
