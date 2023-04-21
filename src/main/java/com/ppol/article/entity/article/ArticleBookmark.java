package com.ppol.article.entity.article;

import com.ppol.article.entity.global.BaseEntity;
import com.ppol.article.entity.user.User;

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

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private Article article;

	private Boolean isBookmark;

	@PrePersist
	public void prePersist() {
		this.isBookmark = this.isBookmark == null || this.isBookmark;
	}

	public void update() {
		this.isBookmark = !this.isBookmark;
	}
}
