package com.ppol.personal.service.album;

import org.springframework.stereotype.Service;

import com.ppol.personal.entity.personal.Album;
import com.ppol.personal.entity.personal.AlbumContent;

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

	// service
	private final AlbumReadService albumReadService;

	/**
	 * 앨범의 기본 내용을 수정하는 메서드
	 */
	@Transactional
	public void updateAlbumBasic(Long userId, Long albumId, Long roomId) {

		Album album = albumReadService.getAlbumWithAuth(albumId, roomId, userId);
	}

	/**
	 * 앨범 콘텐츠의 내용을 수정하는 메서드
	 */
	@Transactional
	public void updateAlbumContent(Long userId, Long albumId, Long roomId, Long contentId) {

		AlbumContent content = albumReadService.getAlbumContentWithAuth(contentId, albumId, roomId, userId);
	}
}
