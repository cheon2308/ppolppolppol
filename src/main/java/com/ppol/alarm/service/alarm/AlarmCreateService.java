package com.ppol.alarm.service.alarm;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ppol.alarm.dto.common.AlarmReferenceDto;
import com.ppol.alarm.dto.request.AlarmRequestDto;
import com.ppol.alarm.entity.alarm.Alarm;
import com.ppol.alarm.entity.alarm.AlarmReference;
import com.ppol.alarm.repository.jpa.AlarmReferenceRepository;
import com.ppol.alarm.repository.jpa.AlarmRepository;
import com.ppol.alarm.service.user.UserReadService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 새로운 알람 생성 기능들을 담당하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AlarmCreateService {

	// repositories
	private final AlarmRepository alarmRepository;
	private final AlarmReferenceRepository referenceRepository;

	// services
	private final AlarmSseService alarmSseService;
	private final UserReadService userReadService;

	/**
	 * 새로운 알람을 생성해서 DB에 저장하고 사용자에게 SSE를 통한 실시간 알람 전달
	 */
	@Transactional
	public void createAlarm(AlarmRequestDto alarmRequestDto) {

		Alarm alarm = alarmRepository.save(alarmRequestMapping(alarmRequestDto));

		createAlarmReferenceList(alarmRequestDto.getAlarmReferenceDtoList(), alarm);

		alarmSseService.sendAlarm(alarm.getId(), alarmRequestDto.getUserId());
	}

	/**
	 * {@link AlarmRequestDto}정보를 이용해 새로운 엔티티 생성
	 */
	public Alarm alarmRequestMapping(AlarmRequestDto alarmRequestDto) {
		return Alarm.builder()
			.alarmType(alarmRequestDto.getAlarmType())
			.user(userReadService.getUser(alarmRequestDto.getUserId()))
			.build();
	}

	/**
	 * 알람 참조 데이터들을 디비에 저장
	 */
	public void createAlarmReferenceList(List<AlarmReferenceDto> alarmReferenceDtoList, Alarm alarm) {

		for (int i = 0; i < alarmReferenceDtoList.size(); i++) {
			referenceRepository.save(referenceRequestMapping(alarmReferenceDtoList.get(i), alarm, i));
		}
	}

	/**
	 * 알람 참조 데이터 매핑
	 */
	public AlarmReference referenceRequestMapping(AlarmReferenceDto alarmReferenceDto, Alarm alarm, int referenceId) {
		return AlarmReference.builder()
			.referenceId(referenceId)
			.alarm(alarm)
			.data(alarmReferenceDto.getData())
			.type(alarmReferenceDto.getType())
			.targetId(alarmReferenceDto.getTargetId())
			.build();
	}
}
