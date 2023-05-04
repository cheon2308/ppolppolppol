package com.ppol.article.entity.user;

import com.ppol.article.entity.global.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 사용자 간의 팔로우 관계를 나타내기 위한 Entity
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class Follow extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 팔로우를 한 사용자
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "follower")
	private User follower;

	// 팔로우 대상 사용자
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "following")
	private User following;

	// 팔로우 여부
	private Boolean isFollow;

	// 해당 유저에 대한 알람 여부
	private Boolean alarmOn;

	// 최초 생성 시 isFollow, alarmOn을 true로 하기 위한 prePersist
	@PrePersist
	private void prePersist() {
		this.isFollow = this.isFollow == null || this.isFollow;
		this.alarmOn = this.alarmOn == null || this.alarmOn;
	}
}