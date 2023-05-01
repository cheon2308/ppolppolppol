package com.ppol.personal.entity.personal;

import org.hibernate.annotations.Where;

import com.ppol.personal.entity.global.BaseEntity;
import com.ppol.personal.entity.user.User;
import com.ppol.personal.util.constatnt.classes.ValidationConstants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class AlbumContent extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private Album album;

	@Size(max = ValidationConstants.COMMENT_CONTENT_MAX_SIZE)
	@Column(length = ValidationConstants.ALBUM_CONTENT_MAX_SIZE)
	private String content;

	@NotNull
	private String image;

	@NotNull
	private Integer order;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "writer")
	private User writer;

	private int state;

	public void delete() {
		this.state = 1;
	}

	// 콘텐츠 내용 업데이트 메서드
	public void updateContent(String content) {
		this.content = content;
	}
}
