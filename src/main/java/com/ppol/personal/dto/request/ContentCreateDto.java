package com.ppol.personal.dto.request;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
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
	private String content;

	// 사진은 필수
	@NotNull
	private MultipartFile image;
}
