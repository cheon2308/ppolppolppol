package com.ppol.onlineserver.dto.request;

import com.ppol.onlineserver.util.constant.enums.CharacterColor;
import com.ppol.onlineserver.util.constant.enums.FaceType;
import com.ppol.onlineserver.util.constant.enums.MeshType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 사용자의 캐릭터 타입 정보를 담는 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TypeUpdateDto {

	private Long userId;
	private CharacterColor color;
	private MeshType meshType;
	private FaceType faceType;
}
