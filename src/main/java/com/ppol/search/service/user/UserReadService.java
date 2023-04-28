package com.ppol.search.service.user;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ppol.search.dto.response.ArticleListDto;
import com.ppol.search.dto.response.UserResponseDto;
import com.ppol.search.entity.user.Follow;
import com.ppol.search.entity.user.User;
import com.ppol.search.repository.jpa.FollowRepository;
import com.ppol.search.repository.jpa.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 사용자 정보 불러오는 기능을 담당하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserReadService {

	// repositories
	private final UserRepository userRepository;
	private final FollowRepository followRepository;

	/**
	 * user ID 값 리스트를 가지고 {@link ArticleListDto}를 리스트로 바꿔주는 메서드
	 */
	@Transactional
	public List<UserResponseDto> readUserList(List<Long> userIdList, Long userId) {

		return userIdList.stream().map(this::getUser).map(user -> userResponseMapping(user, userId)).toList();
	}

	public User getUser(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("사용자"));
	}

	/**
	 * 기본 유저 Entity를 Response Dto로 매핑하는 메서드
	 */
	private UserResponseDto userResponseMapping(User user, Long userId) {
		return UserResponseDto.builder()
			.userId(user.getId())
			.username(user.getUsername())
			.intro(user.getIntro())
			.image(user.getImage())
			.phone(user.getPhone())
			.isFollow(isFollow(userId, user.getId()))
			.followerCount(user.getFollowerCount())
			.followingCount(user.getFollowingCount())
			.build();
	}

	/**
	 * 특정 유저가 특정 유저를 팔로우 했는지 여부를 확인하는 메서드, null인 경우 false
	 */
	private boolean isFollow(Long userId, Long targetUserId) {
		Follow follow = followRepository.findByFollower_IdAndFollowing_IdAndIsFollow(userId, targetUserId, true)
			.orElse(null);

		return follow != null;
	}
}
