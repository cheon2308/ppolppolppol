package com.ppol.article.service.comment;

import org.springframework.stereotype.Service;

import com.ppol.article.entity.article.ArticleComment;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 댓글 삭제 기능을 담당하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CommentDeleteService {

	// services
	private final CommentReadService commentReadService;

	/**
	 * 해당 댓글에 대해 삭제 권한을 확인하고 불러온 뒤 삭제 처리한다.
	 */
	@Transactional
	public void commentDelete(Long commentId, Long userId) {
		ArticleComment comment = commentReadService.getArticleCommentWithAuth(commentId, userId);

		comment.delete();
	}
}
