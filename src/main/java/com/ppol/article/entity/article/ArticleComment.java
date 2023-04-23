package com.ppol.article.entity.article;

import com.ppol.article.entity.global.BaseComment;
import com.ppol.article.entity.global.BaseEntity;
import com.ppol.article.entity.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 	서비스의 게시글/피드의 댓글을 나타내는 엔티티
 */
@Getter
@NoArgsConstructor
@ToString
@Entity
public class ArticleComment extends BaseComment {

	// 댓글이 달린 게시글
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private Article article;

	// Builder
	@Builder
	public ArticleComment(Long id, String content, User writer, Article article, long parent, int likeCount,
		int state) {

		// 부모 생성자
		super(id, content, writer, parent, likeCount, state);

		// ArticleComment 변수들
		this.article = article;
	}

	public void updateContent(String content) {
		this.content = content;
	}
}
