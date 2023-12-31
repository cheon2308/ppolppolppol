package com.ppol.personal.dto.request;

import org.springframework.web.multipart.MultipartFile;

import com.ppol.personal.util.constatnt.classes.ValidationConstants;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 앨범에 새로운 사진을 넣기 위해 사용할 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ContentCreateDto {

	// 사용자가 원할 경우 메모 느낌으로 텍스트 작성 가능
	@Size(max = ValidationConstants.ALBUM_CONTENT_MAX_SIZE)
	private String content;

	// 사진은 필수
	@NotNull
	private MultipartFile image;
}
