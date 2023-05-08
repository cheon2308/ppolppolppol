package com.ppol.alarm.service.alarm;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.ppol.alarm.repository.SseRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 사용자가 SSE를 구독하고 새로운 알람 발생 시 실시간으로 전송해주는 기능을 하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AlarmSseService {

	// repositories
	private final SseRepository sseRepository;

	// services
	private final AlarmReadService alarmReadService;

	/**
	 * 서비스에 접속한 사용자가 SSE 구독, Emitter 등록
	 */
	@Transactional
	public SseEmitter subscribe(Long userId) {

		// emitter 객체 생성 및 저장
		SseEmitter emitter = sseRepository.save(userId);

		// emitter 만료되면 자동제거
		emitter.onCompletion(() -> sseRepository.deleteById(userId));
		emitter.onTimeout(() -> sseRepository.deleteById(userId));
		emitter.onError((e) -> sseRepository.deleteById(userId));

		// 503 에러를 방지하기 위한 더미 이벤트 전송
		sendToUser(userId, "dummy");

		return emitter;
	}

	/**
	 * 사용자에게 알람 발생 시 알람을 전송
	 */
	public void sendAlarm(Long alarmId, Long userId) {

		sendToUser(userId, alarmReadService.getAlarmResponse(alarmId));
	}

	/**
	 * 사용자에게 메시지 정보 전송, 전송 실패 시 연결을 종료한것이므로 emitter 삭제
	 */
	private void sendToUser(Long userId, Object data) {

		SseEmitter emitter = sseRepository.get(userId);

		if (emitter != null) {
			try {
				emitter.send(SseEmitter.event().data(data));
			} catch (IOException exception) {
				sseRepository.deleteById(userId);
			}
		}
	}
}
