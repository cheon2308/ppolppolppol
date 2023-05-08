package com.ppol.group.dto.request.comment;

import com.ppol.group.util.constatnt.classes.ValidationConstants;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
