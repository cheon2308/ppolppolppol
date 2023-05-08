package com.ppol.group.entity.group;

import org.hibernate.annotations.Where;

import com.ppol.group.entity.global.BaseEntity;
import com.ppol.group.entity.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 그룹에 대한 사용자의 알람 허용 여부를 나타내는 엔티티
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class GroupUserAlarm extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Group group;

	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	// 알람 허용 여부
	private Boolean alarmOn;

	// 알람 허용 여부의 default를 true로 설정하기 위함
	@PrePersist
	public void prePersist() {
		this.alarmOn = this.alarmOn == null || this.alarmOn;
	}

	public void updateAlarmOn() {
		this.alarmOn = !this.alarmOn;
	}
}
