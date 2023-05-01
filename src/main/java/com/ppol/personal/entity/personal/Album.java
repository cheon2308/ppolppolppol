package com.ppol.personal.entity.personal;

import org.hibernate.annotations.Where;

import com.ppol.personal.entity.global.BaseEntity;
import com.ppol.personal.entity.user.User;
import com.ppol.personal.util.constatnt.classes.ValidationConstants;
import com.ppol.personal.util.constatnt.enums.OpenStatus;
import com.ppol.personal.util.converter.OpenStatusConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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

/**
 * 사용자의 개인 공간에 전시할 수 있는 앨범을 나타내는 엔티티
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Where(clause = "state = 0")
public class Album extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 해당 앨범이 포함된 개인 공간 정보
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private PersonalRoom personalRoom;

	// 해당 앨범의 제목
	@NotNull
	@Size(max = ValidationConstants.ALBUM_TITLE_MAX_SIZE)
	@Column(length = ValidationConstants.ALBUM_TITLE_MAX_SIZE)
	private String title;

	// 앨범 소개 글
	@Size(max = ValidationConstants.ALBUM_INTRO_MAX_SIZE)
	@Column(length = ValidationConstants.ALBUM_INTRO_MAX_SIZE)
	private String intro;

	// 해당 앨범의 소유 사용자
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner")
	private User owner;

	// 공개 여부
	@Convert(converter = OpenStatusConverter.class)
	private OpenStatus openStatus;

	// 비 공개 시 설정할 수 있는 퀴즈
	@Size(max = ValidationConstants.QUIZ_MAX_SIZE)
	@Column(length = ValidationConstants.QUIZ_MAX_SIZE)
	private String quiz;

	// 비 공개 시 설정할 수 있는 퀴즈에 대한 정답
	private String answer;

	// 상태 정보
	private int state;

	// 삭제 메서드
	public void delete() {
		this.state = 1;
	}

	// 제목 업데이트
	public void updateTitle(String title) {
		this.title = title;
	}

	// 공개 여부 업데이트 메서드
	public void updateOpenStatus(OpenStatus openStatus) {
		this.openStatus = openStatus;
	}

	// 퀴즈 업데이트 메서드
	public void updateQuiz(String quiz) {
		this.quiz = quiz;
	}

	// 정답 업데이트 메서드
	public void updateAnswer(String answer) {
		this.answer = answer;
	}
}
