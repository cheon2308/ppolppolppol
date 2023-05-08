package com.ppol.search.entity.article;

import java.util.List;

import com.ppol.search.entity.global.BaseComment;
import com.ppol.search.entity.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 서비스의 게시글/피드의 댓글을 나타내는 엔티티
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
	public ArticleComment(Long id, String content, User writer, Article article, List<Long> tagUserList, Long parent,
		int likeCount, int state) {

		// 부모 생성자
		super(id, content, writer, parent, tagUserList, likeCount, state);

		// ArticleComment 변수들
		this.article = article;
	}
}
