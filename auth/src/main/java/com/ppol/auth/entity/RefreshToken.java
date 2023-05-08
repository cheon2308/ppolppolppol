package com.ppol.auth.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
 * 	사용자의 RefreshToken을 저장하는 메서드
 * 	사용자 RefreshToken의 유효성을 검증하기 위해서 DB에 저장하고 비교해서 검증
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class RefreshToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 타겟 사용자
	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	private User user;

	// 리프레쉬 토큰
	private String refreshToken;

	public void updateRefresh(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
