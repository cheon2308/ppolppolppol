package com.ppol.personal.service.comment;

import org.springframework.stereotype.Service;

import com.ppol.personal.dto.request.CommentCreateDto;
import com.ppol.personal.dto.response.CommentResponseDto;
import com.ppol.personal.entity.personal.Album;
import com.ppol.personal.entity.personal.AlbumComment;
import com.ppol.personal.entity.user.User;
import com.ppol.personal.repository.AlbumCommentRepository;
import com.ppol.personal.service.album.AlbumReadService;
import com.ppol.personal.service.user.UserReadService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 특정 앨범에 댓글을 추가하는 기능을 제공하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CommentCreateService {

	// repository
	private final AlbumCommentRepository commentRepository;

	// service
	private final AlbumReadService albumReadService;
	private final UserReadService userReadService;

	/**
	 * 새로운 앨범에 대한 댓글을 추가하는 메서드
	 * 어차피 매핑할 내용이 다 따로 따로 변수로 넣어야 하기 때문에 메서드로 따로 빼지 않음
	 */
	@Transactional
	public CommentResponseDto createComment(Long userId, Long albumId, Long roomId, CommentCreateDto commentCreateDto) {

		Album album = albumReadService.getAlbumWithRoom(albumId, roomId);
		String content = commentCreateDto.getContent();
		User writer = userReadService.getUser(userId);

		AlbumComment comment = commentRepository.save(
			AlbumComment.builder().album(album).content(content).writer(writer).build());

		return CommentResponseDto.of(comment);
	}
}
