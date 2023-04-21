package com.ppol.article.entity.article;

import java.util.List;

import org.hibernate.annotations.Where;

import com.ppol.article.entity.global.BaseEntity;
import com.ppol.article.entity.user.User;
import com.ppol.article.util.constatnt.enums.OpenStatus;
import com.ppol.article.util.converter.OpenStatusConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
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

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Where(clause = "state = 0")
public class Article extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(length = 2000)
	private String content;

	@ElementCollection
	private List<String> imageList;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "writer")
	private User writer;

	@Convert(converter = OpenStatusConverter.class)
	private OpenStatus openStatus;

	private int likeCount;

	private int state;

	public void delete() {
		this.state = 1;
	}

	public void updateOpenStatus(OpenStatus openStatus) {
		this.openStatus = openStatus;
	}

	public void updateContent(String content) {
		this.content = content;
	}

	public void updateLikeCount(boolean isAdd) {
		if(isAdd) {
			likeCount++;
		} else {
			likeCount--;
		}
	}
}
