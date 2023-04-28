package com.ppol.search.entity.article;


import com.ppol.search.entity.global.BaseEntity;
import com.ppol.search.entity.user.User;

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
 * 	사용자와 게시글의 북마크 관계를 나타내는 엔티티
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class ArticleBookmark extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 사용자
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	// 대상 게시글
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private Article article;

	// 북마크 여부
	private Boolean isBookmark;

	// 초기 생성시 isBookmark 값을 따로 지정해주지 않아도 true로 설정되도록 PrePersist를 설정
	@PrePersist
	public void prePersist() {
		this.isBookmark = this.isBookmark == null || this.isBookmark;
	}
}
