package com.ppol.user.service.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ppol.user.dto.request.PasswordDto;
import com.ppol.user.entity.user.User;
import com.ppol.user.repository.jpa.UserRepository;
import com.ppol.user.util.constatnt.enums.Provider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCheckService {

	// repository
	private final UserRepository userRepository;

	// service
	private final UserReadService userReadService;

	// other
	private final BCryptPasswordEncoder passwordEncoder;

	/**
	 * 서버에 이미 username이 존재하는지 확인
	 */
	public boolean checkUsername(String username) {
		return !userRepository.existsByUsername(username);
	}

	/**
	 * 서버에 이미 accountId가 존재하는지 확인 (Provider가 Email인 경우 한정)
	 */
	public boolean checkAccountId(String accountId) {
		return !userRepository.existsByAccountIdAndProvider(accountId, Provider.EMAIL);
	}

	/**
	 * 비밀번호 확인
	 */
	public boolean checkPassword(Long userId, PasswordDto passwordDto) {
		User user = userReadService.getUser(userId);

		return passwordEncoder.matches(passwordDto.getPassword(), user.getPassword());
	}
}
