package com.ppol.user.service.follow;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;

import com.ppol.user.dto.response.UserResponseDto;
import com.ppol.user.entity.user.Follow;
import com.ppol.user.entity.user.User;
import com.ppol.user.repository.jpa.FollowRepository;
import com.ppol.user.repository.jpa.UserRepository;
import com.ppol.user.service.user.UserReadService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FollowReadService {

	// repository
	private final UserRepository userRepository;
	private final FollowRepository followRepository;

	// service
	private final UserReadService userReadService;

	/**
	 * 특정 유저의 팔로우 목록을 불러오는 메서드
	 */
	public Slice<UserResponseDto> readFollowingList(Long targetId, Long userId, Long lastId) {

		return readUserList(targetId, userId, lastId, true);
	}

	/**
	 * 특정 유저의 팔로워 목록을 불러오는 메서드
	 */
	public Slice<UserResponseDto> readFollowerList(Long targetId, Long userId, Long lastId) {

		return readUserList(targetId, userId, lastId, false);
	}

	/**
	 * 팔로우 목록 혹은 팔로윙 목록을 불러오는 메서드
	 */
	private Slice<UserResponseDto> readUserList(Long targetId, Long userId, Long lastId, boolean isFollowing) {

		int followerCount = lastId == null ? Integer.MAX_VALUE : userReadService.getUser(lastId).getFollowerCount();
		lastId = lastId == null ? -1 : lastId;
		targetId = targetId == null ? userId : targetId;

		Pageable pageable = PageRequest.of(0, 100);

		Slice<User> slice;
		if (isFollowing) {
			slice = userRepository.findByUserFollowing(targetId, followerCount, lastId, pageable);
		} else {
			slice = userRepository.findByUserFollower(targetId, followerCount, lastId, pageable);
		}

		List<UserResponseDto> content = slice.stream()
			.map(user -> userReadService.userResponseMapping(user, userId))
			.toList();

		return new SliceImpl<>(content, pageable, slice.hasNext());
	}

	/**
	 * 특정 유저가 특정 유저를 팔로우 했는지 여부를 확인하는 메서드, null인 경우 false
	 */
	public boolean isFollow(Long userId, Long targetUserId) {
		Follow follow = followRepository.findByFollower_IdAndFollowing_Id(userId, targetUserId).orElse(null);

		return follow != null && follow.getIsFollow();
	}
}
