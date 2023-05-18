package com.ppol.personal.dto.request;

import com.ppol.personal.util.constatnt.enums.CharacterColor;
import com.ppol.personal.util.constatnt.enums.FaceType;
import com.ppol.personal.util.constatnt.enums.MeshType;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CharacterDto {

	private Long userId;

	@NotNull
	private CharacterColor color;

	@NotNull
	private MeshType meshType;

	@NotNull
	private FaceType faceType;
}
