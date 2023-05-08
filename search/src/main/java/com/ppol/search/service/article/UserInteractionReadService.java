package com.ppol.search.service.article;

import org.springframework.stereotype.Service;

import com.ppol.search.dto.response.UserInteraction;
import com.ppol.search.entity.article.Article;
import com.ppol.search.entity.article.ArticleBookmark;
import com.ppol.search.entity.article.ArticleCommentLike;
import com.ppol.search.entity.article.ArticleLike;
import com.ppol.search.entity.user.Follow;
import com.ppol.search.repository.jpa.ArticleBookmarkRepository;
import com.ppol.search.repository.jpa.ArticleCommentLikeRepository;
import com.ppol.search.repository.jpa.ArticleLikeRepository;
import com.ppol.search.repository.jpa.FollowRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 게시글/댓글에 대한 사용자 상호작용 불러오기 기능들을 담당하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserInteractionReadService {

	// repositories
	private final ArticleLikeRepository articleLikeRepository;
	private final ArticleBookmarkRepository articleBookmarkRepository;
	private final ArticleCommentLikeRepository articleCommentLikeRepository;
	private final FollowRepository followRepository;

	public UserInteraction getUserInteraction(Long userId, Article article) {
		return getUserInteraction(article.getId(), userId, article.getWriter().getId());
	}

	public UserInteraction getUserInteraction(Long articleId, Long userId, Long target) {
		return UserInteraction.builder()
			.isLike(getArticleLike(userId, articleId))
			.isBookmark(getArticleBookmark(userId, articleId))
			.isFollow(getFollow(userId, target))
			.build();
	}

	public boolean getArticleLike(Long userId, Long articleId) {
		ArticleLike like = articleLikeRepository.findByArticle_IdAndUser_Id(articleId, userId).orElse(null);

		return like != null && like.getIsLike();
	}

	public boolean getArticleBookmark(Long userId, Long articleId) {
		ArticleBookmark bookmark = articleBookmarkRepository.findByArticle_IdAndUser_Id(articleId, userId).orElse(null);

		return bookmark != null && bookmark.getIsBookmark();
	}

	public boolean getArticleCommentLike(Long userId, Long commentId) {
		ArticleCommentLike commentLike = articleCommentLikeRepository.findByArticleComment_IdAndUser_Id(commentId,
			userId).orElse(null);

		return commentLike != null && commentLike.getIsLike();
	}

	public boolean getFollow(Long userId, Long target) {
		Follow follow = followRepository.findByFollower_IdAndFollowing_IdAndIsFollow(userId, target, true).orElse(null);

		return follow != null;
	}
}
