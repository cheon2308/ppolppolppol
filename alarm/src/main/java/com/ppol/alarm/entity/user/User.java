package com.ppol.alarm.entity.user;

import org.hibernate.annotations.Where;

import com.ppol.alarm.entity.global.BaseEntity;
import com.ppol.alarm.util.constatnt.classes.ValidationConstants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 사용자 정보 엔티티
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Where(clause = "state = 0")
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 사용자 이름/닉네임 정보 (중복허용 x), (최대 16자)
	@NotNull
	@Column(length = ValidationConstants.USERNAME_MAX_SIZE, unique = true)
	private String username;

	// 사용자 상태 (1인 경우 삭제된 상태)
	private int state;
}
