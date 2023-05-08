package com.ppol.search.entity.article;

import java.util.List;

import org.hibernate.annotations.Where;

import com.ppol.search.entity.global.BaseArticle;
import com.ppol.search.entity.user.User;
import com.ppol.search.util.constatnt.enums.OpenStatus;
import com.ppol.search.util.converter.OpenStatusConverter;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 서비스의 게시글/피드를 나타내는 엔티티
 */
@Getter
@NoArgsConstructor
@ToString
@Entity
@Where(clause = "state = 0")
public class Article extends BaseArticle {

	// 게시글의 공개여부 (PUBLIC, PRIVATE)
	@Convert(converter = OpenStatusConverter.class)
	private OpenStatus openStatus;

	// Builder
	@Builder
	public Article(Long id, String content, List<String> imageList, User writer, int likeCount,
		int state, OpenStatus openStatus) {

		// 부모 생성자
		super(id, content, imageList, writer, likeCount, state);

		// Article 변수들
		this.openStatus = openStatus;
	}
}
