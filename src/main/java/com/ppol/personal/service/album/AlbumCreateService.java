package com.ppol.personal.service.album;

import org.springframework.stereotype.Service;

import com.ppol.personal.dto.request.AlbumCreateDto;
import com.ppol.personal.dto.request.ContentCreateDto;
import com.ppol.personal.dto.response.AlbumDetailDto;
import com.ppol.personal.dto.response.ContentResponseDto;
import com.ppol.personal.entity.personal.Album;
import com.ppol.personal.entity.personal.AlbumContent;
import com.ppol.personal.entity.personal.PersonalRoom;
import com.ppol.personal.exception.exception.AlbumExceededException;
import com.ppol.personal.repository.AlbumContentRepository;
import com.ppol.personal.repository.AlbumRepository;
import com.ppol.personal.service.room.RoomReadService;
import com.ppol.personal.util.constatnt.classes.ValidationConstants;
import com.ppol.personal.util.s3.S3Uploader;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 앨범의 정보 및 앨범 콘텐츠의 정보를 생성 하는 기능들을 제공하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AlbumCreateService {

	// repository
	private final AlbumRepository albumRepository;
	private final AlbumContentRepository contentRepository;

	// service
	private final RoomReadService roomReadService;
	private final AlbumReadService albumReadService;

	// others
	private final S3Uploader s3Uploader;

	/**
	 * 해당 개인 룸에 새로운 앨범을 생성하는 메서드
	 */
	@Transactional
	public AlbumDetailDto createAlbum(Long roomId, Long userId, AlbumCreateDto albumCreateDto) {

		PersonalRoom room = roomReadService.getRoomOnlyOwner(roomId, userId);

		if(albumRepository.countByPersonalRoom_Id(room.getId()) >= ValidationConstants.ROOM_MAX_ALBUM) {
			throw new AlbumExceededException();
		}

		Album album = albumRepository.save(albumCreateMapping(albumCreateDto, room));

		return AlbumDetailDto.of(album);
	}

	/**
	 * 해당 앨범에 새로운 컨텐츠를 추가하는 메서드
	 */
	@Transactional
	public ContentResponseDto createAlbumContent(Long roomId, Long userId, Long albumId, ContentCreateDto contentCreateDto) {
		Album album = albumReadService.getAlbumWithUser(albumId, roomId, userId);

		// 어차피 매핑할 내용이 다 따로 따로 변수로 넣어야 하기 때문에 메서드로 따로 빼지 않음
		String imageUrl = s3Uploader.upload(contentCreateDto.getImage());
		String content = contentCreateDto.getContent() == null ? "" : contentCreateDto.getContent();

		// 순서는 맨 뒤로 설정 (이전의 컨텐츠가 없는 경우 0)
		AlbumContent lastContent = contentRepository.findTopByAlbum_IdOrderByOrderDesc(albumId).orElse(null);
		int order = lastContent == null ? 0 : lastContent.getOrder() + 1;

		AlbumContent albumContent = contentRepository.save(AlbumContent.builder()
			.album(album)
			.writer(album.getOwner())
			.content(content)
			.image(imageUrl)
			.order(order)
			.build());

		return ContentResponseDto.of(albumContent);
	}

	/**
	 * {@link AlbumCreateDto}를 Entity로 매핑하는 메서드
	 */
	private Album albumCreateMapping(AlbumCreateDto albumCreateDto, PersonalRoom room) {
		return Album.builder()
			.owner(room.getOwner())
			.personalRoom(room)
			.title(albumCreateDto.getTitle())
			.openStatus(albumCreateDto.getOpenStatus())
			.color(albumCreateDto.getColor())
			.quiz(albumCreateDto.getQuiz())
			.answer(albumCreateDto.getAnswer())
			.build();
	}
}
