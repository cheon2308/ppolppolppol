package com.ppol.article.entity.global;



import java.util.List;

import com.ppol.article.entity.user.User;
import com.ppol.article.util.constatnt.classes.ValidationConstants;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 	서비스의 모든 댓글에 대한 Base 엔티티
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@MappedSuperclass
public abstract class BaseComment extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	// 내용은 최대 500자
	@NotNull
	@Column(length = ValidationConstants.COMMENT_CONTENT_MAX_SIZE)
	protected String content;

	// 작성자
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "writer")
	protected User writer;

	// 댓글의 대댓글 ID (없는 경우 NULL)
	protected Long parent;

	// 해당 댓글에 태그된 사용자 ID 리스트
	@ElementCollection
	protected List<Long> tagUserList;

	// 댓글 좋아요 수
	protected int likeCount;

	// 댓글 상태 (0: 정상, 1: 삭제)
	protected int state;

	// 댓글 삭제 메서드
	public void delete() {
		this.state = 1;
		this.content = "삭제된 댓글입니다.";
	}

	// 삭제된 게시글에 대해서 내용을 처리해주기 위한 PrePersist
	@PrePersist
	private void prePersist() {
		if(this.state == 1) {
			this.content = "삭제된 댓글입니다.";
		}
	}
}
