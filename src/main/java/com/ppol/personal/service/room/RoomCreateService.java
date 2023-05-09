package com.ppol.personal.service.room;

import org.springframework.stereotype.Service;

import com.ppol.personal.entity.personal.PersonalRoom;
import com.ppol.personal.entity.user.User;
import com.ppol.personal.repository.PersonalRoomRepository;
import com.ppol.personal.service.user.UserReadService;
import com.ppol.personal.util.constatnt.enums.OpenStatus;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 사용자 개인 방의 정보를 생성 하는 기능들을 제공하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RoomCreateService {

	// repository
	private final PersonalRoomRepository personalRoomRepository;

	// services
	private final UserReadService userReadService;

	/**
	 * 자동으로 방 객체를 생성하기 위한 메서드
	 */
	@Transactional
	public PersonalRoom createRoom(Long userId) {

		User user = userReadService.getUser(userId);

		return personalRoomRepository.save(PersonalRoom.builder().owner(user).openStatus(OpenStatus.PUBLIC).build());
	}
}
