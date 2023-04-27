package com.ppol.user.service.user;

import org.springframework.stereotype.Service;

import com.ppol.user.dto.response.UserResponseDto;
import com.ppol.user.entity.user.User;
import com.ppol.user.repository.jpa.UserRepository;
import com.ppol.user.service.follow.FollowReadService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserReadService {

	// repositories
	private final UserRepository userRepository;

	// service
	private final FollowReadService followReadService;

	/**
	 * 사용자 정보를 불러오는 메서드
	 */
	public UserResponseDto readUser(Long targetUserId, Long userId) {

		Long id = targetUserId == null ? userId : targetUserId;

		return userResponseMapping(getUser(id), userId);
	}

	/**
	 * 기본 유저 Entity를 Response Dto로 매핑하는 메서드
	 */
	public UserResponseDto userResponseMapping(User user, Long userId) {
		return UserResponseDto.builder()
			.userId(user.getId())
			.username(user.getUsername())
			.intro(user.getIntro())
			.image(user.getImage())
			.phone(user.getPhone())
			.isFollow(followReadService.isFollow(userId, user.getId()))
			.followerCount(user.getFollowerCount())
			.followingCount(user.getFollowingCount())
			.build();
	}

	/**
	 * 기본 유저 Entity를 ID값을 통해 가져오는 메서드, 예외처리 포함
	 */
	public User getUser(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("사용자"));
	}
}
