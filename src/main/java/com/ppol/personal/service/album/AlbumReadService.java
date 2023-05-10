package com.ppol.personal.service.album;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ppol.personal.dto.response.AlbumDetailDto;
import com.ppol.personal.dto.response.AlbumListDto;
import com.ppol.personal.entity.personal.Album;
import com.ppol.personal.entity.personal.AlbumContent;
import com.ppol.personal.exception.exception.ForbiddenException;
import com.ppol.personal.exception.exception.InvalidParameterException;
import com.ppol.personal.repository.AlbumContentRepository;
import com.ppol.personal.repository.AlbumRepository;
import com.ppol.personal.util.constatnt.enums.OpenStatus;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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

	/**
	 * 특정 개인 방에 대한 상세 정보를 가져오는 메서드, 비공개의 경우 answer을 확인하는 로직을 포함한다.
	 * 올바른 접근
	 * 1. 앨범이 공개 상태
	 * 2. 앨범의 소유주가 접근
	 * 3. 앨범 퀴즈의 정답과 맞는 answer
	 * 이 외에는 권한 없음 예외처리
	 */
	@Transactional
	public AlbumDetailDto readAlbum(Long userId, Long roomId, Long albumId, String answer) {

		Album album = getAlbumWithRoom(albumId, roomId);

		if (album.getOpenStatus().equals(OpenStatus.PUBLIC) || album.getOwner().getId().equals(userId) || checkAnswer(
			answer, album)) {
			return AlbumDetailDto.of(album);
		} else {
			throw new ForbiddenException("앨범");
		}
	}

	/**
	 * 특정 개인 방에 대한 앨범 정보들을 리스트로 불러오는 메서드
	 */
	public List<AlbumListDto> getAblumList(Long roomId) {
		return albumRepository.findByPersonalRoom_Id(roomId).stream().map(AlbumListDto::of).toList();
	}

	/**
	 * 클라이언트로 받은 앨범 퀴즈의 정답이 맞는지 확인하는 메서드
	 * TODO answer 암호화
	 */
	private boolean checkAnswer(String answer, Album album) {
		return answer != null && album.getAnswer() != null && answer.equals(album.getAnswer());
	}

	/**
	 * 앨범 Entity를 불러올 때 클라이언트로 부터 userId 정보를 통해 올바른 호출인지를 확인하는 메서드
	 */
	public Album getAlbumWithUser(Long albumId, Long roomId, Long userId) {

		Album album = getAlbumWithRoom(albumId, roomId);

		if (!album.getOwner().getId().equals(userId)) {
			throw new ForbiddenException("앨범");
		} else {
			return album;
		}
	}

	/**
	 * 앨범 Entity를 불러올 때 클라이언트로 부터 roomId 정보를 통해 올바른 호출인지를 확인하는 메서드
	 */
	public Album getAlbumWithRoom(Long albumId, Long roomId) {
		Album album = getAlbum(albumId);

		if (!album.getPersonalRoom().getId().equals(roomId)) {
			throw new InvalidParameterException("앨범 불러오기 중 잘못된 개인 방 번호에 대한 요청입니다.");
		} else {
			return album;
		}
	}

	/**
	 * 기본 ID로 앨범 Entity를 불러오는 메서드, 예외처리 포함
	 */
	private Album getAlbum(Long albumId) {
		return albumRepository.findById(albumId).orElseThrow(() -> new EntityNotFoundException("앨범"));
	}
}
