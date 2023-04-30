package com.ppol.personal.service.album;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ppol.personal.entity.personal.Album;
import com.ppol.personal.entity.personal.AlbumContent;
import com.ppol.personal.exception.exception.ForbiddenException;
import com.ppol.personal.repository.AlbumContentRepository;
import com.ppol.personal.repository.AlbumRepository;
import com.ppol.personal.service.room.RoomReadService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 앨범의 정보 및 앨범 콘텐츠의 정보를 불러오는 기능들을 제공하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AlbumReadService {

	// repository
	private final AlbumRepository albumRepository;
	private final AlbumContentRepository albumContentRepository;

	// service
	private final RoomReadService roomReadService;

	/**
	 * 앨범 Entity를 불러올 때 클라이언트로 부터 roomId, userId 정보를 통해 권한을 인증하는 과정을 포함하는 메서드
	 */
	public Album getAlbumWithAuth(Long albumId, Long roomId, Long userId) {

		Album album = getAlbum(albumId);

		if (!album.getPersonalRoom().getId().equals(roomId) || !album.getOwner().getId().equals(userId)) {
			throw new ForbiddenException("앨범");
		} else {
			return album;
		}
	}

	/**
	 * 기본 ID로 앨범 Entity를 불러오는 메서드, 예외처리 포함
	 */
	public Album getAlbum(Long albumId) {
		return albumRepository.findById(albumId).orElseThrow(() -> new EntityNotFoundException("앨범"));
	}

	/**
	 * 앨범 콘텐츠 Entity를 불러올 때 클라이언트로 부터 roomId, userId, albumId 정보를 통해 권한을 인증하는 과정을 포함하는 메서드
	 */
	public AlbumContent getAlbumContentWithAuth(Long contentId, Long albumId, Long roomId, Long userId) {

		AlbumContent content = getAlbumContent(contentId);
		Album album = content.getAlbum();

		if (!album.getPersonalRoom().getId().equals(roomId) || !album.getOwner().getId().equals(userId)
			|| !album.getId().equals(albumId)) {
			throw new ForbiddenException("앨범 콘텐츠");
		} else {
			return content;
		}
	}

	/**
	 * 기본 ID로 앨범 컨텐츠 Entity를 불러오는 메서드, 예외처리 포함
	 */
	public AlbumContent getAlbumContent(Long contentId) {
		return albumContentRepository.findById(contentId).orElseThrow(() -> new EntityNotFoundException("앨범 콘텐츠"));
	}
}
