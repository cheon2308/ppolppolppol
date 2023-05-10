package com.ppol.personal.service.comment;

import org.springframework.stereotype.Service;

import com.ppol.personal.entity.personal.AlbumComment;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 앨범의 특정 댓글을 삭제하는 기능을 제공하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CommentDeleteService {

	// service
	private final CommentReadService commentReadService;

	/**
	 * 댓글을 삭제하는 메서드
	 */
	@Transactional
	public void deleteComment(Long userId, Long roomId, Long albumId, Long commentId) {

		AlbumComment comment = commentReadService.getCommentWithAuth(commentId, albumId, roomId, userId);
		comment.delete();
	}
}
