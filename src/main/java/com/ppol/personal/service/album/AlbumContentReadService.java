package com.ppol.personal.service.album;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;

import com.ppol.personal.dto.response.ContentResponseDto;
import com.ppol.personal.entity.personal.Album;
import com.ppol.personal.entity.personal.AlbumContent;
import com.ppol.personal.exception.exception.ForbiddenException;
import com.ppol.personal.exception.exception.InvalidParameterException;
import com.ppol.personal.repository.AlbumContentRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlbumContentReadService {

	// repository
	private final AlbumContentRepository albumContentRepository;

	/**
	 * 앨범 콘텐츠 목록을 Slice 형태로 불러오는 메서드
	 */
	public Slice<ContentResponseDto> readAlbumContentList(Long userId, Long albumId, Long roomId, Long lastId,
		int size) {

		int lastOrder = lastId == null ? -1 : getAlbumContentWithAuth(lastId, albumId, roomId, userId).getOrder();
		Pageable pageable = PageRequest.of(0, size);

		Slice<AlbumContent> slice = albumContentRepository.findByAlbum_IdAndOrderGreaterThanOrderByOrderAsc(albumId,
			lastOrder, pageable);

		return new SliceImpl<>(slice.stream().map(ContentResponseDto::of).toList(), pageable, slice.hasNext());
	}

	/**
	 * 앨범 콘텐츠 Entity를 불러올 때 클라이언트로 부터 roomId, userId, albumId 정보를 통해 권한을 인증하는 과정을 포함하는 메서드
	 */
	public AlbumContent getAlbumContentWithAuth(Long contentId, Long albumId, Long roomId, Long userId) {

		AlbumContent content = getAlbumContent(contentId);
		Album album = content.getAlbum();

		if (!album.getPersonalRoom().getId().equals(roomId) || !album.getOwner().getId().equals(userId)
			|| !album.getId().equals(albumId)) {
			throw new InvalidParameterException("앨범 콘텐츠 불러오기 중 잘못된 앨범에 대한 요청입니다.");
		} else {
			return content;
		}
	}

	/**
	 * 기본 ID로 앨범 컨텐츠 Entity를 불러오는 메서드, 예외처리 포함
	 */
	private AlbumContent getAlbumContent(Long contentId) {
		return albumContentRepository.findById(contentId).orElseThrow(() -> new EntityNotFoundException("앨범 콘텐츠"));
	}
}
