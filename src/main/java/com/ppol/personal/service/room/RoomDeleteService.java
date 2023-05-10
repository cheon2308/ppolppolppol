package com.ppol.personal.service.room;

import org.springframework.stereotype.Service;

import com.ppol.personal.entity.personal.PersonalRoom;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 사용자 개인 방의 정보를 삭제 하는 기능들을 제공하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RoomDeleteService {

	// service
	private final RoomReadService roomReadService;

	/**
	 * 방 삭제하는 메서드
	 */
	@Transactional
	public void deleteRoom(Long userId) {

		PersonalRoom room = roomReadService.getRoomByUser(userId);

		room.delete();
	}
}
