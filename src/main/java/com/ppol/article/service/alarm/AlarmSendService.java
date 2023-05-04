package com.ppol.article.service.alarm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ppol.article.dto.feign.AlarmReferenceDto;
import com.ppol.article.dto.feign.AlarmRequestDto;
import com.ppol.article.entity.article.Article;
import com.ppol.article.entity.article.ArticleComment;
import com.ppol.article.entity.article.ArticleCommentLike;
import com.ppol.article.entity.article.ArticleLike;
import com.ppol.article.repository.jpa.FollowRepository;
import com.ppol.article.util.constatnt.enums.AlarmType;
import com.ppol.article.util.feign.AlarmFeign;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 사용자 알람을 생성하기 위한 정보들을 알람서버로 보내는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AlarmSendService {

	// repository
	private final FollowRepository followRepository;

	// feign client
	private final AlarmFeign alarmFeign;

	/**
	 * 게시글 좋아요 알람 생성 메서드
	 */
	@Async
	public void makeArticleLikeAlarm(ArticleLike articleLike) {

		List<AlarmReferenceDto> list = new ArrayList<>();

		list.add(AlarmReferenceDto.of(articleLike.getArticle(), 0));
		list.add(AlarmReferenceDto.of(articleLike.getUser(), 1));

		createAlarm(AlarmRequestDto.builder()
			.userId(articleLike.getArticle().getWriter().getId())
			.alarmReferenceDtoList(list)
			.alarmType(AlarmType.ARTICLE_LIKE)
			.build());
	}

	/**
	 * 게시글 댓글 알람 생성 메서드
	 */
	@Async
	public void makeArticleCommentAlarm(ArticleComment articleComment) {

		List<AlarmReferenceDto> list = new ArrayList<>();

		list.add(AlarmReferenceDto.of(articleComment.getArticle(), 0));
		list.add(AlarmReferenceDto.of(articleComment.getWriter(), 1));

		createAlarm(AlarmRequestDto.builder()
			.userId(articleComment.getArticle().getWriter().getId())
			.alarmReferenceDtoList(list)
			.alarmType(AlarmType.ARTICLE_COMMENT)
			.build());
	}

	/**
	 * 댓글에 답글 알람 생성 메서드
	 */
	@Async
	public void makeCommentReplyAlarm(ArticleComment parent, ArticleComment articleComment) {

		List<AlarmReferenceDto> list = new ArrayList<>();

		list.add(AlarmReferenceDto.of(parent, 0));
		list.add(AlarmReferenceDto.of(articleComment.getWriter(), 1));
		list.add(AlarmReferenceDto.of(articleComment, 2));

		createAlarm(AlarmRequestDto.builder()
			.userId(parent.getWriter().getId())
			.alarmReferenceDtoList(list)
			.alarmType(AlarmType.COMMENT_REPLY)
			.build());
	}

	/**
	 * 댓글 좋아요 알람 생성 메서드
	 */
	@Async
	public void makeCommentLikeAlarm(ArticleCommentLike articleCommentLike) {

		List<AlarmReferenceDto> list = new ArrayList<>();

		list.add(AlarmReferenceDto.of(articleCommentLike.getArticleComment(), 0));
		list.add(AlarmReferenceDto.of(articleCommentLike.getUser(), 1));

		createAlarm(AlarmRequestDto.builder()
			.userId(articleCommentLike.getArticleComment().getWriter().getId())
			.alarmReferenceDtoList(list)
			.alarmType(AlarmType.COMMENT_LIKE)
			.build());
	}

	/**
	 * 댓글 태그 알람 생성 메서드
	 */
	@Async
	public void makeCommentTagAlarm(ArticleComment articleComment, List<Long> tagUserList) {

		List<AlarmReferenceDto> list = new ArrayList<>();

		list.add(AlarmReferenceDto.of(articleComment, 0));
		list.add(AlarmReferenceDto.of(articleComment.getWriter(), 1));

		tagUserList.forEach((userId -> {
			createAlarm(AlarmRequestDto.builder()
				.userId(userId)
				.alarmReferenceDtoList(list)
				.alarmType(AlarmType.COMMENT_TAG)
				.build());
		}));
	}

	/**
	 * 팔로우한 사용자의 게시글 알람 생성 메서드
	 */
	@Async
	public void makeFollowingNewArticleAlarm(Article article) {

		List<AlarmReferenceDto> list = new ArrayList<>();

		list.add(AlarmReferenceDto.of(article.getWriter(), 0));
		list.add(AlarmReferenceDto.of(article, 1));

		followRepository.findByFollowing_IdAndAlarmOnAndIsFollow(article.getWriter().getId(), true, true)
			.forEach(follow -> {
				createAlarm(AlarmRequestDto.builder()
					.userId(follow.getFollower().getId())
					.alarmReferenceDtoList(list)
					.alarmType(AlarmType.FOLLOWING_NEW_ARTICLE)
					.build());
			});
	}

	/**
	 * 알람 추가 메서드
	 */
	private void createAlarm(AlarmRequestDto alarmRequestDto) {
		log.info("{}", alarmFeign.alarmCreate(alarmRequestDto));
	}
}
