package com.ppol.alarm.controller;

import org.springframework.data.domain.Slice;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.ppol.alarm.dto.request.AlarmRequestDto;
import com.ppol.alarm.dto.response.AlarmResponseDto;
import com.ppol.alarm.service.alarm.AlarmCreateService;
import com.ppol.alarm.service.alarm.AlarmDeleteService;
import com.ppol.alarm.service.alarm.AlarmReadService;
import com.ppol.alarm.service.alarm.AlarmSseService;
import com.ppol.alarm.service.alarm.AlarmUpdateService;
import com.ppol.alarm.util.request.RequestUtils;
import com.ppol.alarm.util.response.ResponseBuilder;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/alarms")
@RequiredArgsConstructor
public class AlarmController {

	// services
	private final AlarmReadService alarmReadService;
	private final AlarmCreateService alarmCreateService;
	private final AlarmUpdateService alarmUpdateService;
	private final AlarmDeleteService alarmDeleteService;
	private final AlarmSseService alarmSseService;

	// 다른 서비스에서 알람을 추가할 때 사용할 엔드포인트
	@PostMapping
	public ResponseEntity<?> alarmCreate(@RequestBody AlarmRequestDto alarmRequestDto) {

		alarmCreateService.createAlarm(alarmRequestDto);

		return ResponseBuilder.ok("ok");
	}

	// 알람 목록 불러오기
	@GetMapping
	public ResponseEntity<?> alarmListRead(@RequestParam(required = false) Long lastId,
		@RequestParam(defaultValue = "50") int size) {

		Long userId = RequestUtils.getUserId();
		Slice<AlarmResponseDto> returnObject = alarmReadService.readAlarmList(userId, lastId, size);

		return ResponseBuilder.ok(returnObject);
	}

	// 특정 알람에 대해 읽음 표시
	@PutMapping("/{alarmId}")
	public ResponseEntity<?> alarmUpdate(@PathVariable Long alarmId) {

		Long userId = RequestUtils.getUserId();
		AlarmResponseDto returnObject = alarmUpdateService.updateAlarm(alarmId, userId);

		return ResponseBuilder.ok(returnObject);
	}

	// 알람 삭제 처리
	@DeleteMapping("/{alarmId}")
	public ResponseEntity<?> alarmDelete(@PathVariable Long alarmId) {

		Long userId = RequestUtils.getUserId();
		alarmDeleteService.deleteAlarm(alarmId, userId);

		return ResponseBuilder.ok("");
	}

	// 실시간 알람을 위해 SSE 구독하기
	@GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter alarmPub() {

		Long userId = RequestUtils.getUserId();
		return alarmSseService.subscribe(userId);

	}
}
