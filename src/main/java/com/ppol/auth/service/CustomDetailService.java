package com.ppol.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ppol.auth.entity.User;
import com.ppol.auth.exception.exception.ForbiddenException;
import com.ppol.auth.repository.UserRepository;
import com.ppol.auth.security.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomDetailService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByAccountId(username).orElseThrow(() -> new ForbiddenException("user load fail"));

		return new CustomUserDetails(user);
	}
}