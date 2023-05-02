package com.ppol.group.service.comment;

import org.springframework.stereotype.Service;

import com.ppol.group.dto.request.comment.CommentUpdateDto;
import com.ppol.group.entity.group.GroupArticleComment;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 그룹 게시글/피드에 대한 댓글을 수정하는 기능을 제공하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CommentUpdateService {

	// service
	private final CommentReadService commentReadService;

	/**
	 * 특정 댓글을 삭제하는 메서드
	 */
	@Transactional
	public void updateComment(Long groupId, Long articleId, Long commentId, Long userId,
		CommentUpdateDto commentUpdateDto) {

		GroupArticleComment comment = commentReadService.getComment(commentId, groupId, articleId, userId);

		comment.updateContent(commentUpdateDto.getContent());
	}
}
