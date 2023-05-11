package com.ppol.personal.service.alarm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ppol.personal.dto.feign.AlarmReferenceDto;
import com.ppol.personal.dto.feign.AlarmRequestDto;
import com.ppol.personal.entity.personal.Album;
import com.ppol.personal.entity.personal.AlbumComment;
import com.ppol.personal.util.constatnt.enums.AlarmType;
import com.ppol.personal.util.feign.AlarmFeign;

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
	 * 누군가 앨범에 댓글을 단 경우 알람 발생
	 */
	@Async
	public void createAlbumCommentAlarm(Album album, AlbumComment comment) {

		List<AlarmReferenceDto> list = new ArrayList<>();

		list.add(AlarmReferenceDto.of(album, 0));
		list.add(AlarmReferenceDto.of(comment.getWriter(), 1));
		list.add(AlarmReferenceDto.of(comment, 2));

		AlarmRequestDto alarmRequestDto = AlarmRequestDto.builder()
			.userId(album.getOwner().getId())
			.alarmType(AlarmType.GROUP_INVITE)
			.alarmReferenceDtoList(list)
			.alarmImage(comment.getWriter().getImage())
			.build();

		createAlarm(alarmRequestDto);
	}

	/**
	 * 알람 추가 메서드
	 */
	private void createAlarm(AlarmRequestDto alarmRequestDto) {
		log.info("{}", alarmFeign.alarmCreate(alarmRequestDto));
	}
}
