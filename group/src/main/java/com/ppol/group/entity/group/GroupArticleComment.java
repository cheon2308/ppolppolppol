package com.ppol.group.entity.group;

import java.util.List;

import com.ppol.group.entity.global.BaseComment;
import com.ppol.group.entity.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 서비스의 그룹 게시글/피드의 댓글을 나타내는 엔티티
 */
@Getter
@NoArgsConstructor
@ToString
@Entity
public class GroupArticleComment extends BaseComment {

	// 댓글이 달린 게시글
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private GroupArticle groupArticle;

	// Builder
	@Builder
	public GroupArticleComment(Long id, @NotNull String content, User writer, GroupArticle groupArticle, long parent,
		List<Long> tagUserList, int likeCount, int state) {

		// 부모 생성자
		super(id, content, writer, parent, tagUserList, likeCount, state);

		// GroupArticleComment 변수들
		this.groupArticle = groupArticle;
	}

	public void updateContent(String content) {
		this.content = content;
	}
}
