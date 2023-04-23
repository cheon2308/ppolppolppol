package com.ppol.article.controller;

import java.time.LocalDateTime;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ppol.article.dto.request.ArticleCreateDto;
import com.ppol.article.dto.request.ArticleUpdateDto;
import com.ppol.article.dto.request.CommentCreateDto;
import com.ppol.article.dto.request.CommentUpdateDto;
import com.ppol.article.dto.response.ArticleDetailDto;
import com.ppol.article.dto.response.ArticleListDto;
import com.ppol.article.dto.response.CommentDto;
import com.ppol.article.service.ArticleReadService;
import com.ppol.article.service.ArticleSaveService;
import com.ppol.article.service.CommentSaveService;
import com.ppol.article.service.UserInteractionService;
import com.ppol.article.util.request.RequestUtils;
import com.ppol.article.util.response.ResponseBuilder;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {

	// services
	private final ArticleReadService articleReadService;
	private final ArticleSaveService articleSaveService;
	private final CommentSaveService commentSaveService;
	private final UserInteractionService userInteractionService;

	// 게시글 목록 불러오기
	@GetMapping
	public ResponseEntity<?> readArticleList(@RequestParam int size, @RequestParam LocalDateTime timestamp) {

		Long userId = RequestUtils.getUserId();
		Slice<ArticleListDto> returnObject = articleReadService.findArticleList(userId, timestamp, size);

		return ResponseBuilder.ok(returnObject);
	}

	// 게시글 상세 정보 불러오기
	@GetMapping("/{articleId}")
	public ResponseEntity<?> readArticle(@PathVariable Long articleId) {

		Long userId = RequestUtils.getUserId();
		ArticleDetailDto returnObject = articleReadService.findArticle(articleId, userId);

		return ResponseBuilder.ok(returnObject);
	}

	// 게시글 작성하기
	@PostMapping
	public ResponseEntity<?> createArticle(ArticleCreateDto articleCreateDto) {

		Long userId = RequestUtils.getUserId();
		ArticleDetailDto returnObject = articleSaveService.articleCreate(userId, articleCreateDto);

		return ResponseBuilder.created(returnObject);
	}

	// 게시글 수정하기
	@PutMapping("/{articleId}")
	public ResponseEntity<?> updateArticle(@PathVariable Long articleId,
		@RequestBody ArticleUpdateDto articleUpdateDto) {

		Long userId = RequestUtils.getUserId();
		ArticleDetailDto returnObject = null;

		return ResponseBuilder.created(returnObject);
	}

	// 게시글 삭제하기
	@DeleteMapping("/{articleId}")
	public ResponseEntity<?> deleteArticle(@PathVariable Long articleId) {

		Long userId = RequestUtils.getUserId();
		ArticleDetailDto returnObject = null;

		return ResponseBuilder.ok(returnObject);
	}

	// 게시글의 댓글 목록 불러오기
	@GetMapping("/{articleId}/comments")
	public ResponseEntity<?> readCommentList(@PathVariable Long articleId, Pageable pageable) {

		Long userId = RequestUtils.getUserId();
		Slice<CommentDto> returnObject = null;

		return ResponseBuilder.ok(returnObject);

	}

	// 게시글에 댓글 추가하기
	@PostMapping("/{articleId}/comments")
	public ResponseEntity<?> createComment(@PathVariable Long articleId,
		@RequestBody CommentCreateDto commentCreateDto) {

		Long userId = RequestUtils.getUserId();
		CommentDto returnObject = null;

		return ResponseBuilder.created(returnObject);
	}

	// 댓글 수정하기
	@PutMapping("/{articleId}/comments/{commentId}")
	public ResponseEntity<?> updateComment(@PathVariable Long articleId, @PathVariable Long commentId,
		@RequestBody CommentUpdateDto commentUpdateDto) {

		Long userId = RequestUtils.getUserId();
		CommentDto returnObject = null;

		return ResponseBuilder.created(returnObject);
	}

	// 댓글 삭제하기
	@DeleteMapping("/{articleId}/comments/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable Long articleId, @PathVariable Long commentId) {

		Long userId = RequestUtils.getUserId();
		CommentDto returnObject = null;

		return ResponseBuilder.created(returnObject);
	}

	// 게시글 좋아요/취소
	@PutMapping("/{articleId}/like")
	public ResponseEntity<?> updateArticleLike(@PathVariable Long articleId) {

		Long userId = RequestUtils.getUserId();

		return ResponseBuilder.ok("");
	}

	// 게시글 북마크/취소
	@PutMapping("/{articleId}/bookmark")
	public ResponseEntity<?> updateArticleBookmark(@PathVariable Long articleId) {

		Long userId = RequestUtils.getUserId();

		return ResponseBuilder.ok("");
	}

	// 댓글 좋아요/취소
	@PutMapping("/{articleId}/comments/{commentId}/like")
	public ResponseEntity<?> updateCommentLike(@PathVariable Long articleId, @PathVariable Long commentId) {

		Long userId = RequestUtils.getUserId();

		return ResponseBuilder.ok("");
	}
}
