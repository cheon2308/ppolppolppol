package com.ppol.alarm.service.alarm;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;

import com.ppol.alarm.dto.common.AlarmReferenceDto;
import com.ppol.alarm.dto.response.AlarmResponseDto;
import com.ppol.alarm.entity.alarm.Alarm;
import com.ppol.alarm.entity.alarm.AlarmReference;
import com.ppol.alarm.repository.jpa.AlarmReferenceRepository;
import com.ppol.alarm.repository.jpa.AlarmRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 알람 불러오는 기능들을 담당하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AlarmReadService {

	// repositories
	private final AlarmRepository alarmRepository;
	private final AlarmReferenceRepository referenceRepository;

	/**
	 * 사용자의 lastAlarmId 이전 생성된 알람들을 size 만큼 불러오는 메서드
	 */
	@Transactional
	public Slice<AlarmResponseDto> readAlarmList(Long userId, Long lastAlarmId, int size) {
		LocalDateTime timestamp = lastAlarmId == null ? LocalDateTime.now() : getAlarm(lastAlarmId).getCreatedAt();
		Pageable pageable = PageRequest.of(0, size);

		Slice<Alarm> slice = alarmRepository.findByUser_IdAndCreatedAtBeforeOrderByCreatedAtDesc(userId, timestamp,
			pageable);

		List<AlarmResponseDto> content = slice.stream().map(this::alarmResponseMapping).toList();

		return new SliceImpl<>(content, pageable, slice.hasNext());
	}

	/**
	 * 알람 ID를 통해 {@link AlarmResponseDto}를 생성하는 메서드
	 */
	public AlarmResponseDto getAlarmResponse(Long alarmId) {
		return alarmResponseMapping(getAlarm(alarmId));
	}

	/**
	 * 알람 엔티티를 AlarmResponseDto로 매핑하는 메서드
	 */
	public AlarmResponseDto alarmResponseMapping(Alarm alarm) {

		return AlarmResponseDto.builder()
			.alarmId(alarm.getId())
			.content(alarm.getAlarmType().getMessage())
			.state(alarm.getState())
			.alarmType(alarm.getAlarmType().name())
			.userId(alarm.getUser().getId())
			.alarmReferenceDtoList(getAlarmReferenceList(alarm.getId()))
			.build();
	}

	/**
	 * 기본 알람 엔티티를 불러오는 메서드, 예외처리를 포함
	 */
	public Alarm getAlarm(Long alarmId) {
		return alarmRepository.findById(alarmId).orElseThrow(() -> new EntityNotFoundException("알람"));
	}

	/**
	 * 알람 참조 데이터들을 불러와서 DTO 형태의 목록으로 반환하는 메서드
	 */
	private List<AlarmReferenceDto> getAlarmReferenceList(Long alarmId) {
		return referenceRepository.findByAlarm_Id(alarmId).stream().map(this::referenceResponseMapping).toList();
	}

	/**
	 * 알람 참조 데이터 엔티티를 DTO로 변환하는 메서드
	 */
	private AlarmReferenceDto referenceResponseMapping(AlarmReference alarmReference) {
		return AlarmReferenceDto.builder()
			.referenceId(alarmReference.getReferenceId())
			.targetId(alarmReference.getTargetId())
			.type(alarmReference.getType())
			.data(alarmReference.getData())
			.build();
	}
}
