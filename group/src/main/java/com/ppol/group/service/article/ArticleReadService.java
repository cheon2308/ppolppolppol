package com.ppol.group.service.article;

import java.time.LocalDateTime;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.ppol.group.dto.response.article.ArticleDetailDto;
import com.ppol.group.dto.response.article.ArticleListDto;
import com.ppol.group.dto.response.UserResponseDto;
import com.ppol.group.entity.group.GroupArticle;
import com.ppol.group.exception.exception.ForbiddenException;
import com.ppol.group.exception.exception.InvalidParameterException;
import com.ppol.group.repository.jpa.GroupArticleRepository;
import com.ppol.group.service.comment.CommentReadService;
import com.ppol.group.util.DateTimeUtils;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 그룹 게시글/피드를 불러오는 기능들을 제공하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleReadService {

	// repository
	private final GroupArticleRepository articleRepository;

	// services
	private final CommentReadService commentReadService;

	/**
	 * 특정 그룹의 게시글/피드들을 Slice 형태로 불러오는 메서드
	 */
	@Transactional
	public Slice<ArticleListDto> readArticleList(Long groupId, Long lastId, int size) {

		Pageable pageable = PageRequest.of(0, size);

		Slice<GroupArticle> slice;
		if (lastId == null) {
			slice = articleRepository.findByGroup_IdOrderByFixedDescCreatedAtDesc(groupId, pageable);
		} else {
			LocalDateTime timestamp = getArticle(lastId).getCreatedAt();
			slice = articleRepository.findByGroup_IdAndCreatedAtBeforeAndFixedOrderByCreatedAt(groupId, timestamp,
				false, pageable);
		}

		return slice.map(this::articleListMapping);
	}

	/**
	 * 특정 그룹 게시글/피드 Dto를 불러오는 메서드
	 */
	@Transactional
	public ArticleDetailDto readArticle(Long groupId, Long articleId, Long userId) {
		return ArticleDetailDto.of(getArticle(articleId, groupId, userId));
	}

	/**
	 * 그룹 게시글/피드 엔티티를 List Dto로 매핑하는 메서드, 대표 댓글을 불러오는 로직 포함
	 */
	public ArticleListDto articleListMapping(GroupArticle article) {
		return ArticleListDto.builder()
			.articleId(article.getId())
			.groupId(article.getGroup().getId())
			.fixed(article.isFixed())
			.imageList(article.getImageList())
			.content(article.getContent())
			.comment(commentReadService.getArticlePresentComment(article.getId(), article.getWriter().getId()))
			.writer(UserResponseDto.of(article.getWriter()))
			.createdAt(article.getCreatedAt())
			.createString(DateTimeUtils.getString(article.getCreatedAt()))
			.build();
	}

	/**
	 * 그룹 게시글/피드 엔티티를 불러 올 때 사용자의 해당 그룹에 대한 권한을 확인하는 로직을 포함하는 메서드
	 */
	public GroupArticle getArticle(Long articleId, Long groupId, Long userId) {
		GroupArticle article = getArticle(articleId, groupId);

		if (article.getGroup().getUserList().stream().noneMatch(user -> user.getId().equals(userId))) {
			throw new ForbiddenException("그룹 게시글/피드");
		} else {
			return article;
		}
	}

	/**
	 * 그룹 게시글/피드 엔티티를 불러 올 때 작성자인지 권한을 확인하는 로직을 포함하는 메서드
	 */
	public GroupArticle getArticleOnlyWriter(Long articleId, Long groupId, Long writerId) {
		GroupArticle article = getArticle(articleId, groupId);

		if (!article.getWriter().getId().equals(writerId)) {
			throw new ForbiddenException("그룹 게시글/피드");
		} else {
			return article;
		}
	}

	/**
	 * 그룹 게시글/피드 엔티티를 불러 올 때 올바른 그룹 ID를 통한 호출인지 확인하는 로직을 포함하는 메서드
	 */
	private GroupArticle getArticle(Long articleId, Long groupId) {
		GroupArticle article = getArticle(articleId);

		if (!article.getGroup().getId().equals(groupId)) {
			throw new InvalidParameterException("그룹 게시글/피드 불러오기 중 잘못된 그룹 아이디를 통한 호출입니다.");
		} else {
			return article;
		}
	}

	/**
	 * 기본 그룹 게시글/피드 엔티티를 불러오는 메서드, 예외처리 포함
	 */
	private GroupArticle getArticle(Long articleId) {
		return articleRepository.findById(articleId).orElseThrow(() -> new EntityNotFoundException("그룹 게시글/피드"));
	}
}
