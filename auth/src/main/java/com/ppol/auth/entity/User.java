package com.ppol.auth.entity;

import org.hibernate.annotations.Where;

import com.ppol.auth.util.constatnt.classes.ValidationConstants;
import com.ppol.auth.util.constatnt.enums.Provider;
import com.ppol.auth.util.converter.ProviderConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
// accountId와 provider를 unique한 값으로 설정
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"account_id", "provider"})})
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/*
		사용자 계정 ID
		소셜 로그인 : 제공 받은 ID 값
		이메일 로그인 : 이메일
 	*/
	@NotNull
	@Column(name = "account_id")
	private String accountId;

	// 사용자 로그인 타입 정보
	@NotNull
	@Convert(converter = ProviderConverter.class)
	private Provider provider;

	// 사용자 비밀번호 정보 (소셜 로그인 유저의 경우 null)
	private String password;

	// 사용자 이름/닉네임 정보 (중복허용 x), (최대 16자)
	@NotNull
	@Column(length = ValidationConstants.USERNAME_MAX_SIZE, unique = true)
	private String username;

	// 사용자 프로필 이미지 경로
	private String image;

	// 팔로워 수
	private int followerCount;

	// 팔로잉 수
	private int followingCount;

	// 사용자 상태 (1인 경우 삭제된 상태)
	private int state;

	@PrePersist
	private void prePersist() {
		this.image = this.image == null ?
			"https://ppolppol-bucket.s3.ap-northeast-2.amazonaws.com/8a99ebdd-b4b8-45cc-ad26-a4022a2c8d0a.jpg" :
			this.image;
	}
}
