package com.ppol.user.service.user;

import java.util.Objects;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ppol.user.dto.request.PasswordDto;
import com.ppol.user.dto.request.UserUpdateDto;
import com.ppol.user.dto.response.UserResponseDto;
import com.ppol.user.entity.user.User;
import com.ppol.user.exception.exception.DuplicateUsernameException;
import com.ppol.user.exception.exception.NoFileException;
import com.ppol.user.util.s3.S3Uploader;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserUpdateService {

	// services
	private final UserReadService userReadService;
	private final UserCheckService userCheckService;
	private final UserElasticsearchService userElasticsearchService;

	// others
	private final S3Uploader s3Uploader;
	private final BCryptPasswordEncoder passwordEncoder;

	/**
	 * {@link UserUpdateDto}를 통해 사용자의 이름, 소개 정보를 수정하는 메서드
	 */
	@Transactional
	public UserResponseDto updateUser(UserUpdateDto userUpdateDto, Long userId) {
		User user = userReadService.getUser(userId);

		// request dto에 username이 비어 있지 않고 이전의 username과 다르다면 수정을 진행
		if (userUpdateDto.getUsername() != null && !userUpdateDto.getUsername().isBlank() && !Objects.equals(
			userUpdateDto.getUsername(), user.getUsername())) {

			// 바꾸고자 하는 username의 중복체크, 중복인 경우 예외처리
			if (!userCheckService.checkUsername(userUpdateDto.getUsername())) {
				throw new DuplicateUsernameException();
			}

			// maria DB와 elasticsearch에 바뀐 내용 적용
			user.updateUsername(userUpdateDto.getUsername());
			userElasticsearchService.saveUser(userId, userUpdateDto.getUsername());
		}

		// request dto에 intro가 비어 있지 않다면 새로운 내용으로 수정
		if (userUpdateDto.getIntro() != null && !userUpdateDto.getIntro().isBlank()) {
			user.updateIntro(userUpdateDto.getIntro());
		}

		return userReadService.userResponseMapping(user, userId);
	}

	/**
	 * 사용자의 프로필 사진을 업데이트 하는 메서드
	 */
	@Transactional
	public UserResponseDto updateUserProfile(MultipartFile imageFile, Long userId) {

		// 비어있는 파일이 온 경우 예외 처리
		if (imageFile == null || imageFile.isEmpty()) {
			throw new NoFileException();
		}

		User user = userReadService.getUser(userId);

		// 기존의 이미지가 있다면 파일 서버에서 삭제
		if (user.getImage() != null && !user.getImage().isBlank()) {
			s3Uploader.deleteFile(user.getImage());
		}

		// 새로운 파일을 파일 서버에 업로드하고 경로를 Entity에 저장
		String newImageUrl = s3Uploader.upload(imageFile);
		user.updateImage(newImageUrl);

		return userReadService.userResponseMapping(user, userId);
	}

	/**
	 * 사용자의 비밀번호를 업데이트 하는 메서드
	 */
	@Transactional
	public void updateUserPassword(PasswordDto passwordDto, Long userId) {

		User user = userReadService.getUser(userId);

		user.updatePassword(passwordEncoder.encode(passwordDto.getPassword()));
	}
}
