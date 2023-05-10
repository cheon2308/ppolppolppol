package com.ppol.personal.dto.response;

import com.ppol.personal.entity.user.UserCharacter;
import com.ppol.personal.util.constatnt.enums.CharacterColor;
import com.ppol.personal.util.constatnt.enums.FaceType;
import com.ppol.personal.util.constatnt.enums.MeshType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 유저 캐릭터 정보를 담은 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserCharacterDto {

	private Long userId;

	private String name;

	private MeshType meshType;

	private CharacterColor color;

	private FaceType faceType;

	public static UserCharacterDto of (UserCharacter userCharacter) {
		return UserCharacterDto.builder()
			.userId(userCharacter.getUser().getId())
			.name(userCharacter.getUser().getUsername())
			.meshType(userCharacter.getMeshType())
			.color(userCharacter.getColor())
			.faceType(userCharacter.getFaceType())
			.build();
	}
}
