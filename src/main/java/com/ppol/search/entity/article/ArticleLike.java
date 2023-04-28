package com.ppol.search.entity.article;

import org.springframework.boot.autoconfigure.security.SecurityProperties;

import com.ppol.search.entity.global.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 사용자와 게시글의 좋아요 관계를 나타내는 엔티티
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class ArticleLike extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 사용자
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private SecurityProperties.User user;

	// 대상 게시글
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private Article article;

	// 좋아요 여부
	private Boolean isLike;

	// 초기 생성시 isLike 값을 따로 지정해주지 않아도 true로 설정되도록 PrePersist를 설정
	@PrePersist
	public void prePersist() {
		this.isLike = this.isLike == null || this.isLike;
	}

}