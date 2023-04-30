package com.ppol.personal.service.album;

import org.springframework.stereotype.Service;

import com.ppol.personal.dto.request.ContentCreateDto;
import com.ppol.personal.dto.request.AlbumCreateDto;
import com.ppol.personal.entity.personal.Album;
import com.ppol.personal.entity.personal.AlbumContent;
import com.ppol.personal.entity.personal.PersonalRoom;
import com.ppol.personal.repository.AlbumContentRepository;
import com.ppol.personal.repository.AlbumRepository;
import com.ppol.personal.service.room.RoomReadService;
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
	public void createAlbum(Long roomId, Long userId, AlbumCreateDto albumCreateDto) {

		PersonalRoom room = roomReadService.getRoomOnlyOwner(roomId, userId);

		albumRepository.save(albumCreateMapping(albumCreateDto, room));
	}

	/**
	 * 해당 앨범에 새로운 컨텐츠를 추가하는 메서드
	 */
	@Transactional
	public void createAlbumContent(Long roomId, Long userId, Long albumId,
		ContentCreateDto contentCreateDto) {

		Album album = albumReadService.getAlbumWithAuth(albumId, roomId, userId);

		// 어차피 매핑할 내용이 다 따로 따로 변수로 넣어야 하기 때문에 메서드로 따로 빼지 않음
		String imageUrl = s3Uploader.upload(contentCreateDto.getImage());
		String content = contentCreateDto.getContent() == null ? "" : contentCreateDto.getContent();

		AlbumContent albumContent = contentRepository.save(AlbumContent.builder()
			.album(album)
			.writer(album.getOwner())
			.content(content)
			.image(imageUrl)
			.build());
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
			.quiz(albumCreateDto.getQuiz())
			.answer(albumCreateDto.getAnswer())
			.build();
	}
}
