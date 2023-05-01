package com.ppol.user.service.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ppol.user.dto.request.UserCreateDto;
import com.ppol.user.dto.response.UserResponseDto;
import com.ppol.user.entity.user.User;
import com.ppol.user.repository.jpa.UserRepository;
import com.ppol.user.service.follow.FollowUpdateService;
import com.ppol.user.util.constatnt.enums.Provider;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSaveService {

	// repositories
	private final UserRepository userRepository;

	// services
	private final UserReadService userReadService;
	private final UserElasticsearchService userElasticsearchService;
	private final FollowUpdateService followUpdateService;

	// others
	private final BCryptPasswordEncoder passwordEncoder;

	/**
	 * 사용자 회원가입 시 새로운 유저정보를 생성
	 */
	@Transactional
	public UserResponseDto createUser(UserCreateDto userCreateDto) {

		// Provider 정보가 없다면 EMAIL
		if (userCreateDto.getProvider() == null) {
			userCreateDto.setProvider(Provider.EMAIL);
		}

		// MariaDB에 저장
		User user = userRepository.save(userCreateMapping(userCreateDto));

		// 엘라스틱 서치에 저장
		userElasticsearchService.saveUser(user.getId(), user.getUsername());

		// 자신을 팔로우 하도록 설정
		followUpdateService.updateFollow(user.getId(), user.getId());

		return userReadService.userResponseMapping(user, user.getId());
	}

	/**
	 * {@link UserCreateDto}를 통해 새로운 user Entity를 생성하는 메서드
	 */
	public User userCreateMapping(UserCreateDto userCreateDto) {
		return User.builder()
			.accountId(userCreateDto.getAccountId())
			.password(passwordEncoder.encode(userCreateDto.getPassword()))
			.provider(userCreateDto.getProvider())
			.username(userCreateDto.getUsername())
			.build();
	}
}
