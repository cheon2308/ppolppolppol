package com.ppol.group.entity.group;

import org.hibernate.annotations.Where;

import com.ppol.group.entity.global.BaseEntity;
import com.ppol.group.entity.user.User;

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

/**
 * 그룹에 새로운 사용자를 초대했음을 나타내는 Entity
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Where(clause = "state = 0")
public class GroupInvite extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_group")
	private Group group;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	private int state;

	public void delete() {
		this.state = 1;
	}
}
