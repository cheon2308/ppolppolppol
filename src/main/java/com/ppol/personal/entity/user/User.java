package com.ppol.personal.entity.user;

import org.hibernate.annotations.Where;

import com.ppol.personal.entity.global.BaseEntity;
import com.ppol.personal.util.constatnt.classes.ValidationConstants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 	사용자 정보 엔티티
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Where(clause = "state = 0")
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 사용자 이름/닉네임 정보 (중복허용 x), (최대 16자)
	@NotNull
	@Column(length = ValidationConstants.USERNAME_MAX_SIZE, unique = true)
	private String username;

	// 사용자 프로필 이미지 경로
	private String image;

	// 사용자 소개 정보 (최대 50자)
	@Column(length = ValidationConstants.USER_INTRO_MAX_SIZE)
	private String intro;

	// 사용자 전화번호 정보
	private String phone;

	// 팔로워 수
	private int followerCount;

	// 팔로잉 수
	private int followingCount;

	// 사용자 상태 (1인 경우 삭제된 상태)
	private int state;
}
