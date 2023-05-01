package com.ppol.personal.service.room;

import org.springframework.stereotype.Service;

import com.ppol.personal.dto.request.RoomUpdateDto;
import com.ppol.personal.entity.personal.PersonalRoom;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 사용자 개인 방의 기본 정보들을 수정 하는 기능들을 제공하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RoomUpdateService {

	// service
	private final RoomReadService roomReadService;

	/**
	 * 방 기본 정보 수정하는 메서드
	 */
	@Transactional
	public void updateRoom(Long userId, RoomUpdateDto roomUpdateDto) {

		PersonalRoom room = roomReadService.getRoomByUser(userId);

	}
}
