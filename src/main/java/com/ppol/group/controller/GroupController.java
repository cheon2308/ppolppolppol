package com.ppol.group.controller;

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

import com.ppol.group.dto.request.article.ArticleCreateDto;
import com.ppol.group.dto.request.article.ArticleUpdateDto;
import com.ppol.group.dto.request.comment.CommentCreateDto;
import com.ppol.group.dto.request.comment.CommentUpdateDto;
import com.ppol.group.dto.request.group.GroupCreateDto;
import com.ppol.group.dto.request.group.GroupUpdateDto;
import com.ppol.group.dto.request.invite.InviteAnswerDto;
import com.ppol.group.dto.request.invite.InviteCreateDto;
import com.ppol.group.dto.response.article.ArticleDetailDto;
import com.ppol.group.dto.response.article.ArticleListDto;
import com.ppol.group.dto.response.CommentResponseDto;
import com.ppol.group.dto.response.group.GroupDetailDto;
import com.ppol.group.dto.response.group.GroupListDto;
import com.ppol.group.dto.response.InviteResponseDto;
import com.ppol.group.util.request.RequestUtils;
import com.ppol.group.util.response.ResponseBuilder;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {

	/**
	 * Groups
	 */
	// 그룹 생성
	@PostMapping
	public ResponseEntity<?> createGroup(@RequestBody GroupCreateDto groupCreateDto) {

		Long userId = RequestUtils.getUserId();
		GroupDetailDto returnObject = null;

		return ResponseBuilder.ok(returnObject);
	}

	// 그룹 기본 정보 수정
	@PutMapping("/{groupId}")
	public ResponseEntity<?> updateGroup(@RequestBody GroupUpdateDto groupUpdateDto, @PathVariable Long groupId) {

		Long userId = RequestUtils.getUserId();
		GroupDetailDto returnObject = null;

		return ResponseBuilder.ok(returnObject);
	}

	// 그룹 삭제
	@DeleteMapping("/{groupId}")
	public ResponseEntity<?> deleteGroup(@PathVariable Long groupId) {

		Long userId = RequestUtils.getUserId();

		return ResponseBuilder.ok("");
	}

	// 특정 그룹 상세정보 불러오기
	@GetMapping("/{groupId}")
	public ResponseEntity<?> getGroup(@PathVariable Long groupId) {

		Long userId = RequestUtils.getUserId();
		GroupDetailDto returnObject = null;

		return ResponseBuilder.ok(returnObject);
	}

	// 사용자가 참여중인 그룹 목록 불러오기
	@GetMapping
	public ResponseEntity<?> getGroupList() {

		Long userId = RequestUtils.getUserId();
		Slice<GroupListDto> returnObject = null;

		return ResponseBuilder.ok(returnObject);
	}

	/**
	 * Articles
	 */
	// 특정 그룹에 게시글/피드 작성
	@PostMapping("/{groupId}/articles")
	public ResponseEntity<?> createArticle(@PathVariable Long groupId, ArticleCreateDto articleCreateDto) {

		Long userId = RequestUtils.getUserId();
		ArticleDetailDto returnObject = null;

		return ResponseBuilder.ok(returnObject);
	}

	// 특정 게시글/피드 수정
	@PutMapping("/{groupId}/articles/{articleId}")
	public ResponseEntity<?> updateArticle(@PathVariable Long groupId, @PathVariable Long articleId,
		@RequestBody ArticleUpdateDto articleUpdateDto) {

		Long userId = RequestUtils.getUserId();
		ArticleDetailDto returnObject = null;

		return ResponseBuilder.ok(returnObject);
	}

	// 특정 게시글/피드 삭제
	@PutMapping("/{groupId}/articles/{articleId}")
	public ResponseEntity<?> updateArticle(@PathVariable Long groupId, @PathVariable Long articleId) {

		Long userId = RequestUtils.getUserId();

		return ResponseBuilder.ok("Deleted");
	}

	// 특정 그룹의 게시글/피드 Slice 형태로 목록 불러오기
	@GetMapping("/{groupId}/articles")
	public ResponseEntity<?> readArticleList(@PathVariable Long groupId, @RequestParam(required = false) Long lastId,
		@RequestParam(defaultValue = "20") int size) {

		Long userId = RequestUtils.getUserId();
		Slice<ArticleListDto> returnObject = null;

		return ResponseBuilder.ok(returnObject);
	}

	// 특정 게시글/피드 상세정보 불러오기
	@PutMapping("/{groupId}/articles/{articleId}")
	public ResponseEntity<?> readArticle(@PathVariable Long groupId, @PathVariable Long articleId) {

		Long userId = RequestUtils.getUserId();
		ArticleDetailDto returnObject = null;

		return ResponseBuilder.ok(returnObject);
	}

	/**
	 * Comments
	 */
	// 특정 게시글/피드에 댓글 달기
	@PostMapping("/{groupId}/articles/{articleId}/comments")
	public ResponseEntity<?> createComment(@PathVariable Long groupId, @PathVariable Long articleId,
		@RequestBody CommentCreateDto commentCreateDto) {

		Long userId = RequestUtils.getUserId();
		CommentResponseDto returnObject = null;

		return ResponseBuilder.ok(returnObject);
	}

	// 특정 게시글/피드에 댓글 수정
	@PostMapping("/{groupId}/articles/{articleId}/comments/{commentId}")
	public ResponseEntity<?> updateComment(@PathVariable Long groupId, @PathVariable Long articleId,
		@PathVariable Long commentId, @RequestBody CommentUpdateDto commentUpdateDto) {

		Long userId = RequestUtils.getUserId();
		CommentResponseDto returnObject = null;

		return ResponseBuilder.ok(returnObject);
	}

	// 특정 게시글/피드에 댓글 삭제
	@PostMapping("/{groupId}/articles/{articleId}/comments/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable Long groupId, @PathVariable Long articleId,
		@PathVariable Long commentId) {

		Long userId = RequestUtils.getUserId();

		return ResponseBuilder.ok("Deleted");
	}

	// 특정 게시글/피드에 댓글 목록 불러오기
	@GetMapping("/{groupId}/articles/{articleId}/comments")
	public ResponseEntity<?> readCommentList(@PathVariable Long groupId, @PathVariable Long articleId,
		@RequestParam(required = false) Long lastId, @RequestParam(defaultValue = "20") int size) {

		Long userId = RequestUtils.getUserId();
		Slice<CommentResponseDto> returnObject = null;

		return ResponseBuilder.ok(returnObject);
	}

	/**
	 * Group User Interactions
	 */
	// 그룹에 사용자 초대
	@PostMapping("/{groupId}/invite")
	public ResponseEntity<?> createUserInvite(@PathVariable Long groupId, @RequestBody InviteCreateDto inviteCreateDto) {

		Long userId = RequestUtils.getUserId();
		InviteResponseDto returnObject = null;

		return ResponseBuilder.ok(returnObject);
	}

	// 초대에 대한 사용자 응답
	@PostMapping("/{groupId}/users")
	public ResponseEntity<?> createUserInvite(@PathVariable Long groupId, @RequestBody InviteAnswerDto inviteCreateDto) {

		Long userId = RequestUtils.getUserId();
		String returnObject = null;

		return ResponseBuilder.ok(returnObject);
	}

	/**
	 * others
	 */
	// 특정 게시글/피드 상단 고정
	@PutMapping("/{groupId}/articles{articleId}/top")
	public ResponseEntity<?> updateArticleFixTop(@PathVariable Long groupId, @PathVariable Long articleId) {

		Long userId = RequestUtils.getUserId();
		String returnObject = null;

		return ResponseBuilder.ok(returnObject);
	}

}
