package com.ppol.alarm.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * 서비스에 접속해서 알람을 받기위해 연결된 사용자들의 SSE Emitter들을 관리하기 위한 Repository Class
 */
@Repository
public class SseRepository {
	// SSE 타임 아웃 시간 10분으로 설정 (만료 시 클라이언트에서 계속 채팅목록에 있다면 자동으로 갱신해줌)
	private static final Long SSE_TIMEOUT = 10 * 60 * 1000L;

	// 사용자 ID에 따라 Emitter 객체를 저장해놓음
	private final Map<Long, SseEmitter> emitterMap = new HashMap<>();

	/**
	 * 특정 유저의 새로운 Emitter 등록
	 */
	public SseEmitter save(Long userId) {

		SseEmitter emitter = new SseEmitter(SSE_TIMEOUT);

		emitterMap.put(userId, emitter);

		return emitter;
	}

	/**
	 * 특정 유저의 Emitter 삭제
	 */
	public void deleteById(Long userId) {
		emitterMap.remove(userId);
	}

	/**
	 * 특정 유저의 Emitter 조회
	 */
	public SseEmitter get(Long userId) {
		return emitterMap.get(userId);
	}
}
