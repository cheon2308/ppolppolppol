package com.ppol.personal.service.room;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ppol.personal.dto.response.AlbumListDto;
import com.ppol.personal.dto.response.RoomResponseDto;
import com.ppol.personal.dto.response.UserCharacterDto;
import com.ppol.personal.entity.personal.PersonalRoom;
import com.ppol.personal.exception.exception.ForbiddenException;
import com.ppol.personal.repository.PersonalRoomRepository;
import com.ppol.personal.service.album.AlbumReadService;
import com.ppol.personal.service.user.UserReadService;

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
	private final UserReadService userReadService;
	private final AlbumReadService albumReadService;
	private final RoomCreateService roomCreateService;

	/**
	 * 방 정보를 DTO로 불러오는 메서드
	 */
	public RoomResponseDto readRoom(Long userId, Long targetUserId) {

		PersonalRoom room = targetUserId == null ? getRoomByUser(userId) : getRoomByUser(targetUserId);

		List<AlbumListDto> albums = albumReadService.getAblumList(room.getId());
		List<UserCharacterDto> npc = userReadService.getNpcList(userId).stream().map(UserCharacterDto::of).toList();

		return RoomResponseDto.builder()
			.roomId(room.getId())
			.roomType(room.getRoomMap())
			.title(room.getOwner().getUsername() + "님의 개인 방")
			.isMyRoom(room.getOwner().getId().equals(userId) ? 1 : 0)
			.roomOwner(UserCharacterDto.of(userReadService.getUserCharacter(room.getOwner().getId())))
			.player(UserCharacterDto.of(userReadService.getUserCharacter(userId)))
			.npc(npc)
			.albums(albums)
			.build();
	}

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

	/**
	 * 특정 사용자의 개인 방 엔티티를 불러오는 메서드
	 */
	public PersonalRoom getRoomByUser(Long userId) {
		return personalRoomRepository.findByOwner_Id(userId).orElseGet(() -> roomCreateService.createRoom(userId));
	}
}
