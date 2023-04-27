package com.ppol.auth.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ppol.auth.entity.User;
import com.ppol.auth.repository.UserRepository;
import com.ppol.auth.util.constatnt.enums.Provider;
import com.ppol.auth.util.constatnt.enums.global.CommonEnumValueConverter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomDetailService implements UserDetailsService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Value("${social.password-key}")
	private String PASSWORD_KEY;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		String code = username.substring(0, 1);
		username = username.substring(1);

		if (code.equals("E")) {
			User user = userRepository.findByAccountIdAndProvider(username, Provider.EMAIL)
				.orElseThrow(() -> new UsernameNotFoundException(""));

			return new CustomUserDetails(user);
		} else {
			Provider provider = CommonEnumValueConverter.ofCode(Provider.class, code);
			assert provider != null;

			String finalUsername = username;

			User user = userRepository.findByAccountIdAndProvider(username, provider)
				.orElseGet(() -> saveSocialUser(finalUsername, provider));

			return new CustomUserDetails(user);
		}
	}

	private User saveSocialUser(String accountId, Provider provider) {

		String password = passwordEncoder.encode(accountId + PASSWORD_KEY);
		String username = "user-" + provider.getCode() + "-" + accountId;

		return userRepository.save(
			User.builder().accountId(accountId).provider(provider).password(password).username(username).build());
	}
}