package com.ppol.group.dto.request.group;

import com.ppol.group.util.constatnt.classes.ValidationConstants;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class GroupUpdateDto {

	@NotNull
	@Size(max = ValidationConstants.GROUP_TITLE_MAX_SIZE)
	private String title;
}
