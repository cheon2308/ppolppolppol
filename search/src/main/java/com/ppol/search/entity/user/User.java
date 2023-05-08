package com.ppol.search.entity.user;

import org.hibernate.annotations.Where;

import com.ppol.search.entity.global.BaseEntity;

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
 * 	게시글 부분에서 필요한 정보만 사용한다.
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

	// 사용자 이름/닉네임 정보
	@NotNull
	@Column(length = 16, unique = true)
	private String username;

	// 사용자 프로필 이미지 경로
	private String image;

	// 사용자 소개 정보 (최대 50자)
	@Column(length = 50)
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
