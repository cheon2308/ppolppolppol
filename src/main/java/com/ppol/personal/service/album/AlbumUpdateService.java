package com.ppol.personal.service.album;

import org.springframework.stereotype.Service;

import com.ppol.personal.dto.request.AlbumUpdateDto;
import com.ppol.personal.dto.request.ContentUpdateDto;
import com.ppol.personal.dto.response.AlbumDetailDto;
import com.ppol.personal.dto.response.ContentResponseDto;
import com.ppol.personal.entity.personal.Album;
import com.ppol.personal.entity.personal.AlbumContent;
import com.ppol.personal.util.constatnt.enums.OpenStatus;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 앨범의 정보 및 앨범 콘텐츠의 정보를 업데이트 하는 기능들을 제공하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AlbumUpdateService {

	// services
	private final AlbumReadService albumReadService;
	private final AlbumContentReadService contentReadService;

	/**
	 * 앨범의 기본 내용을 수정하는 메서드
	 */
	@Transactional
	public AlbumDetailDto updateAlbumBasic(Long userId, Long albumId, Long roomId, AlbumUpdateDto albumUpdateDto) {

		Album album = albumReadService.getAlbumWithUser(albumId, roomId, userId);

		if (albumUpdateDto.getTitle() != null && !albumUpdateDto.getTitle().isBlank()) {
			album.updateTitle(albumUpdateDto.getTitle());
		}

		if (albumUpdateDto.getOpenStatus() != null) {
			album.updateOpenStatus(albumUpdateDto.getOpenStatus());
		}

		if (album.getOpenStatus().equals(OpenStatus.PRIVATE)) {
			if (albumUpdateDto.getQuiz() != null && !albumUpdateDto.getQuiz().isBlank()) {
				album.updateQuiz(albumUpdateDto.getQuiz());
			}

			if (albumUpdateDto.getAnswer() != null && !albumUpdateDto.getAnswer().isBlank()) {
				album.updateAnswer(albumUpdateDto.getAnswer());
			}
		}

		return AlbumDetailDto.of(album);
	}

	/**
	 * 앨범 콘텐츠의 내용을 수정하는 메서드
	 */
	@Transactional
	public ContentResponseDto updateAlbumContent(Long userId, Long albumId, Long roomId, Long contentId,
		ContentUpdateDto contentUpdateDto) {

		AlbumContent content = contentReadService.getAlbumContentWithAuth(contentId, albumId, roomId, userId);

		content.updateContent(contentUpdateDto.getContent());

		return ContentResponseDto.of(content);
	}
}
