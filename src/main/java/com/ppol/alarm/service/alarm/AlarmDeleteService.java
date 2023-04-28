package com.ppol.alarm.service.alarm;

import org.springframework.stereotype.Service;

import com.ppol.alarm.entity.alarm.Alarm;
import com.ppol.alarm.exception.exception.ForbiddenException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 알람 삭제 기능들을 담당하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AlarmDeleteService {

	// services
	private final AlarmReadService alarmReadService;

	/**
	 * 알람 삭제
	 */
	@Transactional
	public void deleteAlarm(Long alarmId, Long userId) {
		Alarm alarm = alarmReadService.getAlarm(alarmId);

		if (alarm.getUser().getId().equals(userId)) {
			alarm.delete();
		} else {
			throw new ForbiddenException("알람 삭제");
		}
	}
}
