package com.ppol.group.service.article;

import org.springframework.stereotype.Service;

import com.ppol.group.entity.group.GroupArticle;
import com.ppol.group.exception.exception.ForbiddenException;
import com.ppol.group.exception.exception.InvalidParameterException;
import com.ppol.group.repository.jpa.GroupArticleRepository;

import jakarta.persistence.EntityNotFoundException;
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

	/**
	 * 그룹 게시글/피드 엔티티를 불러 올 때 사용자의 해당 그룹에 대한 권한을 확인하는 로직을 포함하는 메서드
	 */
	public GroupArticle getArticle(Long articleId, Long groupId, Long userId) {
		GroupArticle article = getArticle(articleId, groupId);

		if(article.getGroup().getUserList().stream().noneMatch(user -> user.getId().equals(userId))) {
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

		if(!article.getWriter().getId().equals(writerId)) {
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

		if(!article.getGroup().getId().equals(groupId)) {
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
