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
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 	서비스의 모든 게시글/피드에 대한 Base 엔티티
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class BaseArticle extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	// 내용은 최대 2000자
	@NotNull
	@Column(length = ValidationConstants.ARTICLE_CONTENT_MAX_SIZE)
	protected String content;

	// 특정 게시글에 포함되는 이미지 리스트 (최대 10개)
	@ElementCollection
	protected List<String> imageList;

	// 게시글 작성자
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "writer")
	protected User writer;

	// 게시글 좋아요 수
	protected int likeCount;

	// 상태 (0: 정상, 1: 삭제)
	protected int state;

	// 게시글 삭제 메서드
	public void delete() {
		this.state = 1;
	}
}
