package com.ppol.group.entity.game;

import org.hibernate.annotations.Where;

import com.ppol.group.entity.global.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 	OX 퀴즈의 한 문제를 나타내는 엔티티
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Where(clause = "state = 0")
public class OxQuiz extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 퀴즈의 문제
	@NotNull
	private String question;

	// O or X
	@NotNull
	private Boolean answer;
}
