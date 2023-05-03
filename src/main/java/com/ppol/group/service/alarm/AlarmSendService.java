package com.ppol.group.service.alarm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ppol.group.dto.request.alarm.AlarmReferenceDto;
import com.ppol.group.dto.request.alarm.AlarmRequestDto;
import com.ppol.group.entity.group.Group;
import com.ppol.group.entity.group.GroupArticle;
import com.ppol.group.entity.group.GroupUserAlarm;
import com.ppol.group.entity.user.User;
import com.ppol.group.repository.jpa.GroupUserAlarmRepository;
import com.ppol.group.util.constatnt.enums.AlarmType;
import com.ppol.group.util.feign.AlarmFeign;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 알람 서버에 알람을 추가하는 기능을 호출하는 기능을 제공하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AlarmSendService {

	// repository
	private final GroupUserAlarmRepository alarmRepository;

	// feign client
	private final AlarmFeign alarmFeign;

	/**
	 * 그룹에 다른 사용자를 초대했을 때 알람 생성하는 메서드
	 */
	@Async
	public void createInviteAlarm(Group group, User user, Long userId) {

		List<AlarmReferenceDto> list = new ArrayList<>();

		list.add(AlarmReferenceDto.of(user, 0));
		list.add(AlarmReferenceDto.of(group, 1));

		AlarmRequestDto alarmRequestDto = AlarmRequestDto.builder()
			.userId(userId)
			.alarmType(AlarmType.GROUP_INVITE)
			.alarmReferenceDtoList(list)
			.build();

		createAlarm(alarmRequestDto);
	}

	/**
	 * 그룹에 다른 사용자가 참여 했을 때 알람
	 */
	@Async
	public void createUserEnterAlarm(Group group, User targetUser) {

		List<GroupUserAlarm> userList = alarmRepository.findByGroup_IdAndAlarmOn(group.getId(), true);

		List<AlarmReferenceDto> list = new ArrayList<>();
		list.add(AlarmReferenceDto.of(group, 0));
		list.add(AlarmReferenceDto.of(targetUser, 1));

		userList.stream().map(GroupUserAlarm::getUser).forEach(user -> {
			AlarmRequestDto alarmRequestDto = AlarmRequestDto.builder()
				.userId(user.getId())
				.alarmType(AlarmType.GROUP_NEW_USER)
				.alarmReferenceDtoList(list)
				.build();

			createAlarm(alarmRequestDto);
		});
	}

	/**
	 * 그룹에 새로운 게시글 생성 시 알람
	 */
	@Async
	public void createNewArticleAlarm(Group group, GroupArticle article) {

		List<GroupUserAlarm> userList = alarmRepository.findByGroup_IdAndAlarmOn(group.getId(), true);

		List<AlarmReferenceDto> list = new ArrayList<>();
		list.add(AlarmReferenceDto.of(group, 0));
		list.add(AlarmReferenceDto.of(article, 1));

		userList.stream().map(GroupUserAlarm::getUser).forEach(user -> {
			AlarmRequestDto alarmRequestDto = AlarmRequestDto.builder()
				.userId(user.getId())
				.alarmType(AlarmType.GROUP_NEW_ARTICLE)
				.alarmReferenceDtoList(list)
				.build();

			createAlarm(alarmRequestDto);
		});
	}

	/**
	 * 알람 추가 메서드
	 */
	private void createAlarm(AlarmRequestDto alarmRequestDto) {
		log.info("{}", alarmFeign.alarmCreate(alarmRequestDto));
	}
}
