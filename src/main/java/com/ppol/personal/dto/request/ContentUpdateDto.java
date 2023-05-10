package com.ppol.personal.dto.request;

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
 * 앨범의 컨텐츠의 텍스트 내용을 수정하기 위한 DTO
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ContentUpdateDto {

	// 텍스트 내용만 수정이 가능하므로 필수
	@NotNull
	@Size(max = ValidationConstants.ALBUM_CONTENT_MAX_SIZE)
	private String content;
}
