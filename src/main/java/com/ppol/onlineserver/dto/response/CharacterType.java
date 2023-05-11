package com.ppol.onlineserver.dto.response;

import java.io.Serializable;

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
 * 캐릭터 구성 타입
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CharacterType implements Serializable {

	private CharacterColor color;
	private MeshType meshType;
	private FaceType faceType;
}
