package com.ppol.onlineserver.entity;

import com.ppol.onlineserver.entity.global.BaseEntity;
import com.ppol.onlineserver.util.constant.enums.CharacterColor;
import com.ppol.onlineserver.util.constant.enums.FaceType;
import com.ppol.onlineserver.util.constant.enums.MeshType;
import com.ppol.onlineserver.util.converter.CharacterColorConverter;
import com.ppol.onlineserver.util.converter.FaceTypeConverter;
import com.ppol.onlineserver.util.converter.MeshTypeConverter;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 사용자 캐릭터 정보 엔티티
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class UserCharacter extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 해당 캐릭터의 사용자
	@NotNull
	@OneToOne
	private User user;

	// 해당 캐릭터의 색상
	@NotNull
	@Convert(converter = CharacterColorConverter.class)
	private CharacterColor color;

	// 해당 캐릭터의 종류
	@NotNull
	@Convert(converter = MeshTypeConverter.class)
	private MeshType meshType;

	// 해당 캐릭터의 얼굴
	@NotNull
	@Convert(converter = FaceTypeConverter.class)
	private FaceType faceType;

	public void updateColor(CharacterColor color) {
		this.color = color;
	}

	public void updateMeshType(MeshType meshType) {
		this.meshType = meshType;
	}

	public void updateFaceType(FaceType faceType) {
		this.faceType = faceType;
	}
}
