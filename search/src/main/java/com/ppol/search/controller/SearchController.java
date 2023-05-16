package com.ppol.search.controller;

import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ppol.search.dto.response.ArticleListDto;
import com.ppol.search.dto.response.UserResponseDto;
import com.ppol.search.service.article.ArticleSearchService;
import com.ppol.search.service.user.UserSearchService;
import com.ppol.search.util.request.RequestUtils;
import com.ppol.search.util.response.ResponseBuilder;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/searching")
@RequiredArgsConstructor
public class SearchController {

	// services
	private final ArticleSearchService articleSearchService;
	private final UserSearchService userSearchService;

	// 해쉬 태그 검색
	@GetMapping("/articles/hash-tag")
	public ResponseEntity<?> articleSearchByHashTag(@RequestParam(defaultValue = "20") int size,
		@RequestParam(defaultValue = "0") int page, @RequestParam String keyword) {

		Long userId = RequestUtils.getUserId();
		Slice<ArticleListDto> returnObject = articleSearchService.searchArticleByHashTag(userId, page, size, keyword);

		return ResponseBuilder.ok(returnObject);
	}

	// 내용 검색
	@GetMapping("/articles/content")
	public ResponseEntity<?> articleSearchByContent(@RequestParam(defaultValue = "20") int size,
		@RequestParam(defaultValue = "0") int page, @RequestParam String keyword) {

		Long userId = RequestUtils.getUserId();
		Slice<ArticleListDto> returnObject = articleSearchService.searchArticleByContent(userId, page, size, keyword);

		return ResponseBuilder.ok(returnObject);
	}

	@GetMapping("/users")
	public ResponseEntity<?> userSearch(@RequestParam(defaultValue = "20") int size,
		@RequestParam(defaultValue = "0") int page, @RequestParam String keyword) {

		Long userId = RequestUtils.getUserId();
		Slice<UserResponseDto> returnObject = userSearchService.searchUser(userId, page, size, keyword);

		return ResponseBuilder.ok(returnObject);
	}
}
