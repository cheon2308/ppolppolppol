package com.ppol.personal.service.room;

import org.springframework.stereotype.Service;

import com.ppol.personal.entity.personal.PersonalRoom;
import com.ppol.personal.exception.exception.ForbiddenException;
import com.ppol.personal.repository.PersonalRoomRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 사용자 개인 방의 정보를 불러오는 기능들을 제공하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RoomReadService {

	// repository
	private final PersonalRoomRepository personalRoomRepository;

	// service

	/**
	 * 기본 ID를 통해 방 엔티티를 찾고 사용자 ID를 통해 권한이 있는지 확인하는 메서드 (반드시 소유주만 접근해야 하는 경우 사용)
	 */
	public PersonalRoom getRoomOnlyOwner(Long roomId, Long userId) {
		PersonalRoom room = getRoom(roomId);

		// 권한 없음 처리
		if (!room.getOwner().getId().equals(userId)) {
			throw new ForbiddenException("개인 방");
		} else {
			return room;
		}
	}

	/**
	 * 기본 ID를 통해 방 엔티티를 불러오는 메서드, 예외처리 포함
	 */
	public PersonalRoom getRoom(Long roomId) {
		return personalRoomRepository.findById(roomId).orElseThrow(() -> new EntityNotFoundException("개인 방"));
	}
}
