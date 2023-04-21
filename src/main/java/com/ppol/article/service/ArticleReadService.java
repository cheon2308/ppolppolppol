package com.ppol.article.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ppol.article.dto.response.ArticleDetailDto;
import com.ppol.article.dto.response.ArticleListDto;
import com.ppol.article.dto.response.UserDto;
import com.ppol.article.entity.article.Article;
import com.ppol.article.repository.jpa.ArticleRepository;
import com.ppol.article.util.DateTimeUtils;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleReadService {

	// repositories
	private final ArticleRepository articleRepository;

	// services
	private final UserInteractionService userInteractionService;
	private final CommentReadService commentReadService;

	// others

	public Slice<ArticleListDto> findArticleList(Long userId, LocalDateTime timestamp, int size) {

		Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "createdAt"));

		Slice<Article> articleSlice = articleRepository.findArticlesByFollowedUsers(userId, timestamp, pageable);
		List<ArticleListDto> contentList = articleSlice.stream()
			.map(article -> articleListMapping(article, userId))
			.toList();

		return new SliceImpl<>(contentList, pageable, articleSlice.hasNext());
	}

	public ArticleDetailDto findArticle(Long articleId, Long userId) {

		Article article = getArticle(articleId);

		return articleDetailMapping(article, userId);
	}

	public Article getArticle(Long articleId) {
		return articleRepository.findById(articleId).orElseThrow(() -> new EntityNotFoundException("게시글"));
	}

	public ArticleDetailDto articleDetailMapping(Article article, Long userId) {

		return ArticleDetailDto.builder()
			.articleId(article.getId())
			.content(article.getContent())
			.userInteraction(userInteractionService.getUserInteraction(userId, article))
			.createString(DateTimeUtils.getString(article.getCreatedAt()))
			.createdAt(article.getCreatedAt())
			.imageList(article.getImageList())
			.writer(UserDto.of(article.getWriter()))
			.openStatus(article.getOpenStatus())
			.build();
	}

	public ArticleListDto articleListMapping(Article article, Long userId) {

		return ArticleListDto.builder()
			.articleId(article.getId())
			.content(article.getContent())
			.userInteraction(userInteractionService.getUserInteraction(userId, article))
			.createString(DateTimeUtils.getString(article.getCreatedAt()))
			.createdAt(article.getCreatedAt())
			.imageList(article.getImageList())
			.writer(UserDto.of(article.getWriter()))
			.openStatus(article.getOpenStatus())
			.comment(commentReadService.getPresentComment(article.getId()))
			.build();
	}
}
