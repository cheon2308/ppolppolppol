package com.ppol.personal.service.album;

import org.springframework.stereotype.Service;

import com.ppol.personal.entity.personal.Album;
import com.ppol.personal.entity.personal.AlbumContent;
import com.ppol.personal.repository.AlbumContentRepository;
import com.ppol.personal.util.s3.S3Uploader;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 앨범의 정보 및 앨범 콘텐츠의 정보를 삭제 하는 기능들을 제공하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AlbumDeleteService {

	// repository
	private final AlbumContentRepository albumContentRepository;

	// service
	private final AlbumReadService albumReadService;

	// others
	private final S3Uploader s3Uploader;

	/**
	 * 앨범 자체를 삭제하는 메서드, 이 때 엘범의 컨텐츠도 모두 삭제하도록 한다.
	 */
	@Transactional
	public void deleteAlbum(Long albumId, Long userId, Long roomId) {

		Album album = albumReadService.getAlbumWithAuth(albumId, roomId, userId);

		album.delete();

		albumContentRepository.findByAlbum_Id(albumId).forEach(this::deleteAlbumContent);
	}

	/**
	 * 특정 앨범 컨텐츠를 삭제하는 메서드, id 값을 통해 컨텐츠를 불러오는 부분을 포함한다.
	 */
	@Transactional
	public void deleteAlbumContent(Long contentId, Long albumId, Long userId, Long roomId) {

		AlbumContent albumContent = albumReadService.getAlbumContentWithAuth(contentId, albumId, roomId, userId);

		deleteAlbumContent(albumContent);
	}

	/**
	 * 앨범 컨텐츠를 삭제하는 메서드, S3서버에서 파일 삭제하는 과정을 포함한다.
	 */
	public void deleteAlbumContent(AlbumContent albumContent) {

		s3Uploader.deleteFile(albumContent.getImage());
		albumContent.delete();
	}
}
