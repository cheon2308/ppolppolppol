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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AlbumCreateDto {

	// 앨범의 제목
	@NotNull
	@Size(max = ValidationConstants.ALBUM_TITLE_MAX_SIZE)
	private String title;

	@NotNull
	private OpenStatus openStatus;

	private String quiz;

	private String answer;
}
