package com.ppol.personal.service.comment;

import java.time.LocalDateTime;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;

import com.ppol.personal.dto.response.CommentResponseDto;
import com.ppol.personal.entity.personal.AlbumComment;
import com.ppol.personal.exception.exception.ForbiddenException;
import com.ppol.personal.exception.exception.InvalidParameterException;
import com.ppol.personal.repository.AlbumCommentRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 앨범 댓글들을 불러오는 기능들을 제공하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CommentReadService {

	// repository
	private final AlbumCommentRepository commentRepository;

	/**
	 * 특정 앨범에 대한 댓글을 Slice 형태로 불러오는 메서드
	 */
	@Transactional
	public Slice<CommentResponseDto> readCommentList(Long userId, Long albumId, Long roomId, Long lastId, int size) {

		LocalDateTime timestamp =
			lastId == null ? LocalDateTime.now() : getCommentWithAuth(lastId, albumId, roomId).getCreatedAt();
		Pageable pageable = PageRequest.of(0, size);

		Slice<AlbumComment> slice = commentRepository.findByAlbum_IdAndCreatedAtBeforeOrderByCreatedAtDesc(albumId,
			timestamp, pageable);

		return new SliceImpl<>(slice.stream().map(CommentResponseDto::of).toList(), pageable, slice.hasNext());
	}

	/**
	 * 특정 댓글을 가져올 때 작성자인지 확인하는 과정을 포함하는 메서드
	 */
	public AlbumComment getCommentWithAuth(Long commentId, Long albumId, Long roomId, Long userId) {
		AlbumComment comment = getCommentWithAuth(commentId, albumId, roomId);

		if(!comment.getWriter().getId().equals(userId)) {
			throw new ForbiddenException("앨범 댓글");
		} else {
			return comment;
		}
	}

	/**
	 * 특정 댓글을 가져올 때, roomId와 albumId를 통해 올바른 호출인지 확인하기 위한 메서드
	 */
	public AlbumComment getCommentWithAuth(Long commentId, Long albumId, Long roomId) {
		AlbumComment comment = getComment(commentId);

		if (!comment.getAlbum().getId().equals(albumId) || !comment.getAlbum()
			.getPersonalRoom()
			.getId()
			.equals(roomId)) {
			throw new InvalidParameterException("앨범 댓글 불러오기 중 roomId 혹은 albumId가 잘못됨");
		} else {
			return comment;
		}
	}

	/**
	 * 특정 댓글을 찾는 메서드
	 */
	public AlbumComment getComment(Long commentId) {
		return commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("앨범 댓글"));
	}
}
