package com.ppol.personal.entity.personal;

import org.hibernate.annotations.Where;

import com.ppol.personal.entity.global.BaseEntity;
import com.ppol.personal.entity.user.User;
import com.ppol.personal.util.constatnt.classes.ValidationConstants;
import com.ppol.personal.util.constatnt.enums.OpenStatus;
import com.ppol.personal.util.constatnt.enums.RoomMap;
import com.ppol.personal.util.converter.OpenStatusConverter;
import com.ppol.personal.util.converter.RoomMapConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 사용자의 3D 개인 공간을 나타내는 Entity
 * 랜덤 이동을 구현 할 때 공개/비공개 여부 및 퀴즈등을 설정하기 위함
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Where(clause = "state = 0")
public class PersonalRoom extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 해당 공간의 주인
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

	// 개인 방의 맵 정보
	@NotNull
	@Convert(converter = RoomMapConverter.class)
	private RoomMap roomMap;

	// 상태 정보
	private int state;

	// 삭제 메서드 (사용자 회원 탈퇴 시)
	public void delete() {
		this.state = 1;
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

	public void updateRoomMap(RoomMap roomMap) {
		this.roomMap = roomMap;
	}
}
