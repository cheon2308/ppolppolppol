package com.ppol.auth.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.ppol.auth.entity.User;
import com.ppol.auth.repository.UserRepository;
import com.ppol.auth.service.CustomDetailService;
import com.ppol.auth.util.constatnt.enums.Provider;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private final CustomDetailService customDetailService;
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if (!(authentication instanceof CustomAuthenticationToken customAuthentication)) {
			return null;
		}

		String username = customAuthentication.getName();
		String password = customAuthentication.getCredentials().toString();
		Provider provider = customAuthentication.getProvider();

		UserDetails userDetails;

		if (provider.equals(Provider.EMAIL)) {
			userDetails = customDetailService.loadUserByUsername(username);
			// Check the password here, for example, using PasswordEncoder
			// ...
		} else {
			User user = userRepository.findByAccountId(username)
				.orElseGet(() -> saveNewUser(username, password));

			userDetails = new CustomUserDetails(user);
		}

		return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
	}

	private User saveNewUser(String accountId, String password) {

		User user = User.builder()
			.accountId(accountId)
			.password(passwordEncoder.encode(password))
			// TODO default username 설정 해야 함
			.build();

		return userRepository.save(user);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
