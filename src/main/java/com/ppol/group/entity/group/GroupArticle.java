package com.ppol.group.entity.group;

import java.util.List;

import org.hibernate.annotations.Where;

import com.ppol.group.entity.global.BaseArticle;
import com.ppol.group.entity.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 	서비스에서 그룹만의 게시글/피드를 나타내는 엔티티
 * 	해당 엔티티가 필요한 이유 : 그룹 끼리만 공유하는 게시글/피드를 따로 관리하기 위함
 */
@Getter
@NoArgsConstructor
@ToString
@Entity
@Where(clause = "state = 0")
public class GroupArticle extends BaseArticle {

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uer_group")
	private Group group;

	// Builder
	@Builder
	public GroupArticle(Long id, String content, List<String> imageList, User writer, int likeCount, int state,
		Group group) {

		// 부모 생성자
		super(id, content, imageList, writer, likeCount, state);

		// GroupArticle 변수들
		this.group = group;
	}
}
