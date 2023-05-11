package com.ppol.user.service.alarm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ppol.user.dto.feign.AlarmReferenceDto;
import com.ppol.user.dto.feign.AlarmRequestDto;
import com.ppol.user.entity.user.Follow;
import com.ppol.user.util.constatnt.enums.AlarmType;
import com.ppol.user.util.feign.AlarmFeign;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 알람 서버에 알람을 추가하는 기능을 호출하는 기능을 제공하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AlarmSendService {

	// feign client
	private final AlarmFeign alarmFeign;

	/**
	 * 팔로우에 대한 알람 생성
	 */
	@Async
	public void createFollowAlarm(Follow follow) {

		List<AlarmReferenceDto> list = new ArrayList<>();

		list.add(AlarmReferenceDto.of(follow.getFollower(), 0));

		createAlarm(AlarmRequestDto.builder()
			.userId(follow.getFollowing().getId())
			.alarmType(AlarmType.FOLLOW)
			.alarmReferenceDtoList(list)
			.alarmImage(follow.getFollower().getImage())
			.build());
	}

	/**
	 * 알람 추가 메서드
	 */
	private void createAlarm(AlarmRequestDto alarmRequestDto) {
		log.info("{}", alarmFeign.alarmCreate(alarmRequestDto));
	}
}
