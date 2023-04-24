package com.ppol.article.service.comment;

import org.springframework.stereotype.Service;

import com.ppol.article.dto.request.CommentUpdateDto;
import com.ppol.article.dto.response.CommentDto;
import com.ppol.article.entity.article.ArticleComment;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 댓글 수정 기능을 담당하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CommentUpdateService {

	// services
	private final CommentReadService commentReadService;

	/**
	 *	해당 댓글에 대해 삭제 권한을 확인하고 불러온 뒤 내용을 수정한다.
	 */
	@Transactional
	public CommentDto commentUpdate(CommentUpdateDto commentUpdateDto, Long commentId, Long userId) {

		ArticleComment comment = commentReadService.getArticleCommentWithAuth(commentId, userId);

		comment.updateContent(commentUpdateDto.getContent());

		return commentReadService.commentMapping(comment, userId);
	}
}
