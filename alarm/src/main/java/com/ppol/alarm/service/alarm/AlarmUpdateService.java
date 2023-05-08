package com.ppol.alarm.service.alarm;

import org.springframework.stereotype.Service;

import com.ppol.alarm.dto.response.AlarmResponseDto;
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
public class AlarmUpdateService {

	// services
	private final AlarmReadService alarmReadService;

	/**
	 * 알람 읽음 처리
	 */
	@Transactional
	public AlarmResponseDto updateAlarm(Long alarmId, Long userId) {
		Alarm alarm = alarmReadService.getAlarm(alarmId);

		if (alarm.getUser().getId().equals(userId)) {
			alarm.updateRead();

			return alarmReadService.alarmResponseMapping(alarm);
		} else {
			throw new ForbiddenException("알람 수정");
		}
	}
}
