package com.ppol.article.service.user;

import org.springframework.stereotype.Service;

import com.ppol.article.entity.article.Article;
import com.ppol.article.entity.article.ArticleBookmark;
import com.ppol.article.entity.article.ArticleComment;
import com.ppol.article.entity.article.ArticleCommentLike;
import com.ppol.article.entity.article.ArticleLike;
import com.ppol.article.repository.jpa.ArticleBookmarkRepository;
import com.ppol.article.repository.jpa.ArticleCommentLikeRepository;
import com.ppol.article.repository.jpa.ArticleLikeRepository;
import com.ppol.article.service.alarm.AlarmSendService;
import com.ppol.article.service.article.ArticleReadService;
import com.ppol.article.service.comment.CommentReadService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 게시글/댓글에 대한 사용자 상호작용 업데이트 기능들을 담당하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserInteractionUpdateService {

	// repositories
	private final ArticleLikeRepository articleLikeRepository;
	private final ArticleBookmarkRepository articleBookmarkRepository;
	private final ArticleCommentLikeRepository articleCommentLikeRepository;

	// services
	private final ArticleReadService articleReadService;
	private final CommentReadService commentReadService;
	private final UserReadService userReadService;
	private final AlarmSendService alarmSendService;

	/**
	 * 특정 사용자의 특정 게시글에 대한 좋아요 여부를 업데이트 하는 메서드
	 */
	@Transactional
	public void articleLikeUpdate(Long articleId, Long userId) {

		ArticleLike articleLike = articleLikeRepository.findByArticle_IdAndUser_Id(articleId, userId).orElse(null);
		Article article = articleReadService.getArticle(articleId);

		if (articleLike == null) {
			articleLike = articleLikeRepository.save(
				ArticleLike.builder().article(article).user(userReadService.getUser(userId)).build());
		} else {
			articleLike.update();
		}

		article.updateLikeCount(articleLike.getIsLike());

		// 좋아요가 true인 경우 게시글 작성자에게 알람 생성 (비동기)
		if (articleLike.getIsLike()) {
			alarmSendService.makeArticleLikeAlarm(articleLike);
		}
	}

	/**
	 * 특정 사용자의 특정 게시글에 대한 북마크 여부를 업데이트 하는 메서드
	 */
	@Transactional
	public void articleBookmarkUpdate(Long articleId, Long userId) {

		ArticleBookmark articleBookmark = articleBookmarkRepository.findByArticle_IdAndUser_Id(articleId, userId)
			.orElse(null);

		if (articleBookmark == null) {
			articleBookmarkRepository.save(ArticleBookmark.builder()
				.article(articleReadService.getArticle(articleId))
				.user(userReadService.getUser(userId))
				.build());
		} else {
			articleBookmark.update();
		}
	}

	/**
	 * 특정 사용자의 특정 댓글에 대한 좋아요 여부를 업데이트 하는 메서드
	 */
	@Transactional
	public void commentLikeUpdate(Long commentId, Long userId) {

		ArticleCommentLike commentLike = articleCommentLikeRepository.findByArticleComment_IdAndUser_Id(commentId,
			userId).orElse(null);
		ArticleComment comment = commentReadService.getArticleComment(commentId);

		if (commentLike == null) {
			commentLike = articleCommentLikeRepository.save(
				ArticleCommentLike.builder().articleComment(comment).user(userReadService.getUser(userId)).build());
		} else {
			commentLike.update();
		}

		comment.updateLikeCount(commentLike.getIsLike());

		// 좋아요가 true인 경우 댓글 작성자에게 알람 생성 (비동기)
		if (commentLike.getIsLike()) {
			alarmSendService.makeCommentLikeAlarm(commentLike);
		}
	}
}
