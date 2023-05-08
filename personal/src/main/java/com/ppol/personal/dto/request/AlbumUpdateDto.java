package com.ppol.personal.dto.request;

import com.ppol.personal.util.constatnt.classes.ValidationConstants;
import com.ppol.personal.util.constatnt.enums.OpenStatus;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 앨범의 기본 정보를 수정하기 위한 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AlbumUpdateDto {

	// 앨범의 제목
	@Size(max = ValidationConstants.ALBUM_TITLE_MAX_SIZE)
	private String title;

	// 공개 여부
	private OpenStatus openStatus;

	// 퀴즈와 정답 (비공개인 경우에만 사용됨)
	@Size(max = ValidationConstants.QUIZ_MAX_SIZE)
	private String quiz;

	private String answer;
}
