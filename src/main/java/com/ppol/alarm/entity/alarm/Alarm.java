package com.ppol.alarm.entity.alarm;

import org.hibernate.annotations.Where;

import com.ppol.alarm.entity.global.BaseEntity;
import com.ppol.alarm.entity.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 	서비스의 알람들을 나타내는 엔티티
 * 	알람의 종류는 다양할 수 있음
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Where(clause = "state < 2")
public class Alarm extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/*
		알람의 내용 content
		아마 content 자체를 html 형식으로 가져가서 서버에서 링크를 달아주도록 하는 것이 좋을듯?
	 */
	@NotNull
	private String content;

	// 알람 대상 사용자
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	/*
	  	이 때 state를 통해 알람의 상태를 나타낸다
	   	state = 0 인 경우 읽지 않음
	   	state = 1 인 경우 읽은 상태
	   	state = 2 인 경우 삭제 상태
	 */
	private int state;

	// 해당 알람을 읽은 상태로 표시하는 메서드
	public void updateRead() {
		this.state = 1;
	}

	// 해당 알람을 삭제하는 메서드
	public void delete() {
		this.state = 2;
	}
}
