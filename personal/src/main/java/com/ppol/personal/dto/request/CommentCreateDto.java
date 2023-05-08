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
 * 앨범에 대한 댓글을 작성하기 위한 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CommentCreateDto {

	@NotNull
	@Size(max = ValidationConstants.COMMENT_CONTENT_MAX_SIZE)
	private String content;
}
