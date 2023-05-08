package com.ppol.article.service.user;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;

import com.ppol.article.dto.response.ArticleListDto;
import com.ppol.article.entity.article.Article;
import com.ppol.article.repository.jpa.ArticleRepository;
import com.ppol.article.service.article.ArticleReadService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 사용자와 관련된 게시글들을 불러오는 서비스 (북마크 목록, 작성글 목록)
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserArticleReadService {

	// repositories
	private final ArticleRepository articleRepository;

	// services
	private final ArticleReadService articleReadService;

	/**
	 * 특정 사용자가 작성한 게시글 목록을 불러오는 메서드
	 */
	@Transactional
	public Slice<ArticleListDto> userArticleListRead(Long userId, Long targetUserId, Long lastArticleId, int size) {

		Article article = articleReadService.getArticlePermitNull(lastArticleId);

		LocalDateTime timestamp = article == null ? LocalDateTime.now() : article.getCreatedAt();
		lastArticleId = lastArticleId == null ? -1 : lastArticleId;

		Pageable pageable = PageRequest.of(0, size);

		Slice<Article> articleSlice = articleRepository.findByWriter(targetUserId, timestamp, lastArticleId, pageable);

		List<ArticleListDto> contentList = articleSlice.stream()
			.map(a -> articleReadService.articleListMapping(a, userId))
			.toList();

		return new SliceImpl<>(contentList, pageable, articleSlice.hasNext());
	}

	/**
	 * 특정 사용자가 북마크한 게시글 목록을 불러오는 메서드
	 */
	@Transactional
	public Slice<ArticleListDto> bookmarkArticleListRead(Long userId, Long lastArticleId, int size) {

		Article article = articleReadService.getArticlePermitNull(lastArticleId);

		LocalDateTime timestamp = article == null ? LocalDateTime.now() : article.getCreatedAt();
		lastArticleId = lastArticleId == null ? -1 : lastArticleId;

		Pageable pageable = PageRequest.of(0, size);

		Slice<Article> articleSlice = articleRepository.findByBookmark(userId, timestamp, lastArticleId, pageable);

		List<ArticleListDto> contentList = articleSlice.stream()
			.map(a -> articleReadService.articleListMapping(a, userId))
			.toList();

		return new SliceImpl<>(contentList, pageable, articleSlice.hasNext());
	}

}
