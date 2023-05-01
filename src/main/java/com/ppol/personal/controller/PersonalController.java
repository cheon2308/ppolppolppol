package com.ppol.personal.controller;

import java.util.List;

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

import com.ppol.personal.dto.request.AlbumCreateDto;
import com.ppol.personal.dto.request.AlbumUpdateDto;
import com.ppol.personal.dto.request.CommentCreateDto;
import com.ppol.personal.dto.request.ContentCreateDto;
import com.ppol.personal.dto.request.ContentUpdateDto;
import com.ppol.personal.dto.request.RoomUpdateDto;
import com.ppol.personal.dto.response.AlbumDetailDto;
import com.ppol.personal.dto.response.AlbumListDto;
import com.ppol.personal.dto.response.CommentResponseDto;
import com.ppol.personal.dto.response.ContentResponseDto;
import com.ppol.personal.dto.response.RoomResponseDto;
import com.ppol.personal.service.album.AlbumContentReadService;
import com.ppol.personal.service.album.AlbumCreateService;
import com.ppol.personal.service.album.AlbumDeleteService;
import com.ppol.personal.service.album.AlbumReadService;
import com.ppol.personal.service.album.AlbumUpdateService;
import com.ppol.personal.service.comment.CommentCreateService;
import com.ppol.personal.service.comment.CommentDeleteService;
import com.ppol.personal.service.comment.CommentReadService;
import com.ppol.personal.service.room.RoomCreateService;
import com.ppol.personal.service.room.RoomDeleteService;
import com.ppol.personal.service.room.RoomReadService;
import com.ppol.personal.service.room.RoomUpdateService;
import com.ppol.personal.util.request.RequestUtils;
import com.ppol.personal.util.response.ResponseBuilder;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/personal")
@RequiredArgsConstructor
public class PersonalController {

	// room
	private final RoomReadService roomReadService;
	private final RoomCreateService roomCreateService;
	private final RoomUpdateService roomUpdateService;
	private final RoomDeleteService roomDeleteService;

	// album
	private final AlbumReadService albumReadService;
	private final AlbumCreateService albumCreateService;
	private final AlbumDeleteService albumDeleteService;
	private final AlbumUpdateService albumUpdateService;

	// album content
	private final AlbumContentReadService albumContentReadService;

	// album comment
	private final CommentReadService commentReadService;
	private final CommentCreateService commentCreateService;
	private final CommentDeleteService commentDeleteService;

	// 개인 룸 생성 (회원 가입 시)
	@PostMapping
	public ResponseEntity<?> createPersonalRoom() {

		Long userId = RequestUtils.getUserId();
		roomCreateService.createRoom(userId);

		return ResponseBuilder.ok("");
	}

	// 개인 룸 정보 변경 (제목, 공개 여부, 퀴즈, 정답)
	@PutMapping
	public ResponseEntity<?> updatePersonalRoom(@RequestBody RoomUpdateDto roomUpdateDto) {

		Long userId = RequestUtils.getUserId();
		roomUpdateService.updateRoom(userId, roomUpdateDto);

		return ResponseBuilder.ok("");
	}

	// 개인 룸 삭제 (사용자 회원탈퇴 시)
	@DeleteMapping
	public ResponseEntity<?> deletePersonalRoom() {

		Long userId = RequestUtils.getUserId();
		roomDeleteService.deleteRoom(userId);

		return ResponseBuilder.ok("");
	}

	// 사용자의 개인 룸 정보 불러오기
	@GetMapping
	public ResponseEntity<?> readPersonalRoom(@RequestParam(required = false) Long roomId) {

		Long userId = RequestUtils.getUserId();
		RoomResponseDto returnObject = roomReadService.readRoom(userId, roomId);

		return ResponseBuilder.ok(returnObject);
	}

	// 무작위 개인 룸 불러오기
	@GetMapping
	public ResponseEntity<?> readRandomRoom() {

		return ResponseBuilder.ok("");
	}

	// 앨범 생성
	@PostMapping("{personalRoomId}/albums")
	public ResponseEntity<?> createAlbum(@PathVariable Long personalRoomId,
		@RequestBody AlbumCreateDto albumCreateDto) {

		Long userId = RequestUtils.getUserId();
		AlbumDetailDto returnObject = albumCreateService.createAlbum(personalRoomId, userId, albumCreateDto);

		return ResponseBuilder.ok(returnObject);
	}

	// 개인룸의 모든 앨범 리스트 불러오기
	@GetMapping("/{personalRoomId}/albums")
	public ResponseEntity<?> readAlbumList(@PathVariable Long personalRoomId) {

		List<AlbumListDto> returnObject = albumReadService.readAblumList(personalRoomId);

		return ResponseBuilder.ok(returnObject);
	}

	// 특정 앨범 상세정보 불러오기
	@GetMapping("/{personalRoomId}/albums/{albumId}")
	public ResponseEntity<?> readAlbum(@PathVariable Long personalRoomId, @PathVariable Long albumId,
		@RequestParam(required = false) String answer) {

		Long userId = RequestUtils.getUserId();
		AlbumDetailDto returnObject = albumReadService.readAlbum(userId, personalRoomId, albumId, answer);

		return ResponseBuilder.ok(returnObject);
	}

	// 앨범 기본 정보 수정 (제목, 위치 등)
	@PutMapping("/{personalRoomId}/albums/{albumId}")
	public ResponseEntity<?> updateAlbum(@PathVariable Long personalRoomId, @PathVariable Long albumId,
		@RequestBody AlbumUpdateDto albumUpdateDto) {

		Long userId = RequestUtils.getUserId();
		AlbumDetailDto returnObject = albumUpdateService.updateAlbumBasic(userId, albumId, personalRoomId,
			albumUpdateDto);

		return ResponseBuilder.ok(returnObject);
	}

	// 앨범 삭제
	@DeleteMapping("/{personalRoomId}/albums/{albumId}")
	public ResponseEntity<?> deleteAlbum(@PathVariable Long personalRoomId, @PathVariable Long albumId) {

		Long userId = RequestUtils.getUserId();
		albumDeleteService.deleteAlbum(albumId, userId, personalRoomId);

		return ResponseBuilder.ok("Deleted");
	}

	// 특정 앨범의 컨텐츠들 불러오기 (무한 스크롤 혹은 페이징)
	@GetMapping("/{personalRoomId}/albums/{albumId}/contents")
	public ResponseEntity<?> readAlbumContentList(@PathVariable Long personalRoomId, @PathVariable Long albumId,
		@RequestParam(required = false) Long lastId, @RequestParam(defaultValue = "20") int size) {

		Long userId = RequestUtils.getUserId();
		Slice<ContentResponseDto> returnObject = albumContentReadService.readAlbumContentList(userId, albumId,
			personalRoomId, lastId, size);

		return ResponseBuilder.ok(returnObject);
	}

	// 특정 앨범의 컨텐츠 생성하기
	@PostMapping("/{personalRoomId}/albums/{albumId}/contents")
	public ResponseEntity<?> createAlbumContent(@PathVariable Long personalRoomId, @PathVariable Long albumId,
		ContentCreateDto contentCreateDto) {

		Long userId = RequestUtils.getUserId();
		ContentResponseDto returnObject = albumCreateService.createAlbumContent(personalRoomId, userId, albumId,
			contentCreateDto);

		return ResponseBuilder.ok(returnObject);
	}

	// 특정 앨범의 컨텐츠 삭제
	@DeleteMapping("/{personalRoomId}/albums/{albumId}/contents/{contentId}")
	public ResponseEntity<?> deleteAlbumContent(@PathVariable Long personalRoomId, @PathVariable Long albumId,
		@PathVariable Long contentId) {

		Long userId = RequestUtils.getUserId();
		albumDeleteService.deleteAlbumContent(contentId, albumId, userId, personalRoomId);

		return ResponseBuilder.ok("Deleted");
	}

	// 특정 앨범의 컨텐츠 내용 수정
	@PutMapping("/{personalRoomId}/albums/{albumId}/contents/{contentId}")
	public ResponseEntity<?> updateAlbumContent(@PathVariable Long personalRoomId, @PathVariable Long albumId,
		@PathVariable Long contentId, @RequestBody ContentUpdateDto contentUpdateDto) {

		Long userId = RequestUtils.getUserId();
		ContentResponseDto returnObject = albumUpdateService.updateAlbumContent(userId, albumId, personalRoomId,
			contentId, contentUpdateDto);

		return ResponseBuilder.ok(returnObject);
	}

	// 특정 앨범의 댓글 불러오기 (무한 스크롤 혹은 페이징)
	@GetMapping("/{personalRoomId}/albums/{albumId}/comments")
	public ResponseEntity<?> readAlbumCommentList(@PathVariable Long personalRoomId, @PathVariable Long albumId,
		@RequestParam(required = false) Long lastId, @RequestParam(defaultValue = "20") int size) {

		Long userId = RequestUtils.getUserId();
		Slice<CommentResponseDto> returnObject = commentReadService.readCommentList(userId, albumId, personalRoomId,
			lastId, size);

		return ResponseBuilder.ok(returnObject);
	}

	// 특정 앨범의 댓글 작성하기
	@PostMapping("/{personalRoomId}/albums/{albumId}/comments")
	public ResponseEntity<?> createAlbumComment(@PathVariable Long personalRoomId, @PathVariable Long albumId,
		@RequestBody CommentCreateDto commentCreateDto) {

		Long userId = RequestUtils.getUserId();
		CommentResponseDto returnObject = commentCreateService.createComment(userId, albumId, personalRoomId,
			commentCreateDto);

		return ResponseBuilder.ok(returnObject);
	}

	// 특정 앨범의 특정 댓글 삭제하기
	@PostMapping("/{personalRoomId}/albums/{albumId}/comments/{commentId}")
	public ResponseEntity<?> deleteAlbumComment(@PathVariable Long personalRoomId, @PathVariable Long albumId,
		@PathVariable Long commentId) {

		Long userId = RequestUtils.getUserId();
		commentDeleteService.deleteComment(userId, personalRoomId, albumId, commentId);

		return ResponseBuilder.ok("deleted");
	}
}
