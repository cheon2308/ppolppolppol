package com.ppol.user.controller;

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
import org.springframework.web.multipart.MultipartFile;

import com.ppol.user.dto.request.PasswordDto;
import com.ppol.user.dto.request.UserCreateDto;
import com.ppol.user.dto.request.UserUpdateDto;
import com.ppol.user.dto.response.UserResponseDto;
import com.ppol.user.service.follow.FollowReadService;
import com.ppol.user.service.follow.FollowUpdateService;
import com.ppol.user.service.user.UserCheckService;
import com.ppol.user.service.user.UserDeleteService;
import com.ppol.user.service.user.UserReadService;
import com.ppol.user.service.user.UserSaveService;
import com.ppol.user.service.user.UserUpdateService;
import com.ppol.user.util.request.RequestUtils;
import com.ppol.user.util.response.ResponseBuilder;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	// user
	private final UserSaveService userSaveService;
	private final UserReadService userReadService;
	private final UserUpdateService userUpdateService;
	private final UserDeleteService userDeleteService;
	private final UserCheckService userCheckService;

	// follow
	private final FollowUpdateService followUpdateService;
	private final FollowReadService followReadService;

	// 사용자 회원 가입
	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody UserCreateDto userCreateDto) {

		UserResponseDto returnObject = userSaveService.createUser(userCreateDto);

		return ResponseBuilder.created(returnObject);
	}

	// 사용자 이름, 소개 정보 수정
	@PutMapping
	public ResponseEntity<?> updateUser(@RequestBody UserUpdateDto userUpdateDto) {

		Long userId = RequestUtils.getUserId();
		UserResponseDto returnObject = userUpdateService.updateUser(userUpdateDto, userId);

		return ResponseBuilder.ok(returnObject);
	}

	// 사용자 프로필 이미지 수정
	@PutMapping("/profile-image")
	public ResponseEntity<?> updateUserProfileImage(MultipartFile image) {

		Long userId = RequestUtils.getUserId();
		UserResponseDto returnObject = userUpdateService.updateUserProfile(image, userId);

		return ResponseBuilder.ok(returnObject);
	}

	// 사용자 회원 탈퇴
	@DeleteMapping
	public ResponseEntity<?> deleteUser() {

		Long userId = RequestUtils.getUserId();
		userDeleteService.deleteUser(userId);

		return ResponseBuilder.ok("");
	}

	// 사용자 정보 불러오기 (targetId가 없다면 자신의 정보를 불러옴)
	@GetMapping
	public ResponseEntity<?> getUser(@RequestParam(required = false) Long targetId) {

		Long userId = RequestUtils.getUserId();
		UserResponseDto returnObject = userReadService.readUser(targetId, userId);

		return ResponseBuilder.ok(returnObject);
	}

	// 사용자 아이디(이메일) 중복 체크
	@GetMapping("/checking-account-id")
	public ResponseEntity<?> checkAccountId(@RequestParam String accountId) {

		boolean returnObject = userCheckService.checkAccountId(accountId);
		return ResponseBuilder.ok(returnObject);
	}

	// 사용자 이름 중복 체크
	@GetMapping("/checking-username")
	public ResponseEntity<?> checkUsername(@RequestParam String username) {

		boolean returnObject = userCheckService.checkUsername(username);
		return ResponseBuilder.ok(returnObject);
	}

	// 사용자 비밀번호 체크
	@PostMapping("/checking-password")
	public ResponseEntity<?> checkPassword(@RequestBody PasswordDto passwordDto) {

		Long userId = RequestUtils.getUserId();
		boolean returnObject = userCheckService.checkPassword(userId, passwordDto);

		return ResponseBuilder.ok(returnObject);
	}

	// 사용자 비밀번호 수정
	@PutMapping("/password")
	public ResponseEntity<?> updatePassword(@RequestBody PasswordDto passwordDto) {

		Long userId = RequestUtils.getUserId();
		userUpdateService.updateUserPassword(passwordDto, userId);

		return ResponseBuilder.ok("");
	}

	// 특정 사용자의 target 사용자에 대한 팔로우 여부 변경
	@PutMapping("/{targetUserId}/follow")
	public ResponseEntity<?> updateFollowUser(@PathVariable Long targetUserId) {

		Long userId = RequestUtils.getUserId();
		boolean returnObject = followUpdateService.updateFollow(userId, targetUserId);

		return ResponseBuilder.ok(returnObject);
	}

	// 특정 사용자 (targetId)의 팔로우한 사용자 목록 불러오기 (targetId가 없을 경우 자신의 팔로우 한 사용자 목록)
	@GetMapping("/followings")
	public ResponseEntity<?> getFollowingUserList(@RequestParam(required = false) Long targetId,
		@RequestParam(required = false) Long lastId) {

		Long userId = RequestUtils.getUserId();
		Slice<UserResponseDto> returnObject = followReadService.readFollowingList(targetId, userId, lastId);

		return ResponseBuilder.ok(returnObject);
	}

	// 특정 사용자 (targetId)를 팔로우한 사용자 목록 불러오기 (targetId가 없을 경우 자신을 팔로우 한 사용자 목록)
	@GetMapping("/followers")
	public ResponseEntity<?> getFollowedUserList(@RequestParam(required = false) Long targetId,
		@RequestParam(required = false) Long lastId) {

		Long userId = RequestUtils.getUserId();
		Slice<UserResponseDto> returnObject = followReadService.readFollowerList(targetId, userId, lastId);

		return ResponseBuilder.ok(returnObject);
	}
}
