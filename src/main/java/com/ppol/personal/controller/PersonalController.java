package com.ppol.personal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ppol.personal.util.request.RequestUtils;
import com.ppol.personal.util.response.ResponseBuilder;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/personal")
@RequiredArgsConstructor
public class PersonalController {

	// 개인 룸 생성 (회원 가입 시)
	@PostMapping
	public ResponseEntity<?> createPersonalRoom() {

		Long userId = RequestUtils.getUserId();

		return ResponseBuilder.ok("");
	}

	// 개인 룸 정보 변경 (제목, 공개 여부, 퀴즈, 정답)
	@PutMapping
	public ResponseEntity<?> updatePersonalRoom() {

		Long userId = RequestUtils.getUserId();

		return ResponseBuilder.ok("");
	}

	// 개인 룸 삭제 (사용자 회원탈퇴 시)
	@DeleteMapping
	public ResponseEntity<?> deletePersonalRoom() {

		Long userId = RequestUtils.getUserId();

		return ResponseBuilder.ok("");
	}

	// 사용자의 개인 룸 정보 불러오기
	@GetMapping
	public ResponseEntity<?> readPersonalRoom() {

		Long userId = RequestUtils.getUserId();

		return ResponseBuilder.ok("");
	}

	// 무작위 개인 룸 불러오기
	@GetMapping
	public ResponseEntity<?> readRandomRoom() {

		return ResponseBuilder.ok("");
	}

	// 앨범 생성
	@PostMapping("{personalRoomId}/albums")
	public ResponseEntity<?> createAlbum(@PathVariable Long personalRoomId) {

		Long userId = RequestUtils.getUserId();

		return ResponseBuilder.ok("");
	}

	// 개인룸의 모든 앨범 리스트 불러오기
	@GetMapping("/{personalRoomId}/albums")
	public ResponseEntity<?> readAlbumList(@PathVariable Long personalRoomId) {

		Long userId = RequestUtils.getUserId();

		return ResponseBuilder.ok("");
	}

	// 특정 앨범 상세정보 불러오기
	@GetMapping("/{personalRoomId}/albums/{albumId}")
	public ResponseEntity<?> readAlbum(@PathVariable Long personalRoomId, @PathVariable Long albumId) {

		Long userId = RequestUtils.getUserId();

		return ResponseBuilder.ok("");
	}

	// 앨범 기본 정보 수정 (제목, 위치 등)
	@PutMapping("/{personalRoomId}/albums/{albumId}")
	public ResponseEntity<?> updateAlbum(@PathVariable Long personalRoomId, @PathVariable Long albumId) {

		Long userId = RequestUtils.getUserId();

		return ResponseBuilder.ok("");
	}

	// 앨범 삭제
	@DeleteMapping("/{personalRoomId}/albums/{albumId}")
	public ResponseEntity<?> deleteAlbum(@PathVariable Long personalRoomId, @PathVariable Long albumId) {

		Long userId = RequestUtils.getUserId();

		return ResponseBuilder.ok("");
	}

	// 특정 앨범의 컨텐츠들 불러오기 (무한 스크롤 혹은 페이징)
	@GetMapping("/{personalRoomId}/albums/{albumId}/contents")
	public ResponseEntity<?> readAlbumContentList(@PathVariable Long personalRoomId, @PathVariable Long albumId,
		@RequestParam(required = false) Long lastId, @RequestParam(defaultValue = "20") int size) {

		Long userId = RequestUtils.getUserId();

		return ResponseBuilder.ok("");
	}

	// 특정 앨범의 컨텐츠 생성하기
	@PostMapping("/{personalRoomId}/albums/{albumId}/contents")
	public ResponseEntity<?> createAlbumContent(@PathVariable Long personalRoomId, @PathVariable Long albumId) {

		Long userId = RequestUtils.getUserId();

		return ResponseBuilder.ok("");
	}

	// 특정 앨범의 컨텐츠 삭제
	@DeleteMapping("/{personalRoomId}/albums/{albumId}/contents/{contentsId}")
	public ResponseEntity<?> deleteAlbumContent(@PathVariable Long personalRoomId, @PathVariable Long albumId,
		@PathVariable Long contentsId) {

		Long userId = RequestUtils.getUserId();

		return ResponseBuilder.ok("");
	}

	// 특정 앨범의 컨텐츠 내용 수정
	@PutMapping("/{personalRoomId}/albums/{albumId}/contents/{contentsId}")
	public ResponseEntity<?> updateAlbumContent(@PathVariable Long personalRoomId, @PathVariable Long albumId,
		@PathVariable Long contentsId) {

		Long userId = RequestUtils.getUserId();

		return ResponseBuilder.ok("");
	}

	// 특정 앨범의 댓글 불러오기 (무한 스크롤 혹은 페이징)
	@GetMapping("/{personalRoomId}/albums/{albumId}/comments")
	public ResponseEntity<?> readAlbumCommentList(@PathVariable Long personalRoomId, @PathVariable Long albumId,
		@RequestParam(required = false) Long lastId, @RequestParam(defaultValue = "20") int size) {

		Long userId = RequestUtils.getUserId();

		return ResponseBuilder.ok("");
	}

	// 특정 앨범의 댓글 작성하기
	@PostMapping("/{personalRoomId}/albums/{albumId}/comments")
	public ResponseEntity<?> createAlbumComment(@PathVariable Long personalRoomId, @PathVariable Long albumId) {

		Long userId = RequestUtils.getUserId();

		return ResponseBuilder.ok("");
	}
}
