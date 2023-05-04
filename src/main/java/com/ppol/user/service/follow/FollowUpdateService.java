package com.ppol.user.service.follow;

import org.springframework.stereotype.Service;

import com.ppol.user.entity.user.Follow;
import com.ppol.user.entity.user.User;
import com.ppol.user.repository.jpa.FollowRepository;
import com.ppol.user.service.alarm.AlarmSendService;
import com.ppol.user.service.user.UserReadService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FollowUpdateService {

	// repository
	private final FollowRepository followRepository;

	// service
	private final UserReadService userReadService;
	private final AlarmSendService alarmSendService;

	/**
	 * 팔로우 정보를 업데이트 하는 메서드
	 */
	@Transactional
	public boolean updateFollow(Long followerId, Long targetUserId) {

		Follow follow = followRepository.findByFollower_IdAndFollowing_Id(followerId, targetUserId).orElse(null);

		User follower = userReadService.getUser(followerId);
		User following = userReadService.getUser(targetUserId);

		if (follow == null) {
			follow = followRepository.save(Follow.builder().follower(follower).following(following).build());
		} else {
			follow.updateFollow();
		}

		if (!followerId.equals(targetUserId)) {
			follower.updateFollowingCount(follow.getIsFollow());
			following.updateFollowerCount(follow.getIsFollow());
		}

		// follow가 true인 경우 알람 전송 (비동기 처리)
		if (follow.getIsFollow()) {
			alarmSendService.createFollowAlarm(follow);
		}

		return follow.getIsFollow();
	}
}
