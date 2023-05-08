package com.ppol.group.entity.group;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Where;

import com.ppol.group.entity.global.BaseEntity;
import com.ppol.group.entity.user.User;
import com.ppol.group.util.constatnt.classes.ValidationConstants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 서비스에서 사용자들간의 그룹을 나타내는 엔티티
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Where(clause = "state = 0")
@Table(name = "user_group")
public class Group extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 그룹의 이름 최대 20자
	@Column(length = ValidationConstants.GROUP_TITLE_MAX_SIZE)
	private String title;

	// 그룹을 생성한 사용자 (사용자 별로 그룹 생성할 수 있는 개수를 제한하기 위해서 필요한 정보)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner")
	private User owner;

	// 그룹에 참여중인 사용자들
	@ManyToMany
	@JoinTable(name = "group_users", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private final Set<User> userList = new HashSet<>();

	// 그룹 상태 (0: 정상, 1: 삭제)
	private int state;

	// 그룹을 삭제하는 메서드
	public void delete() {
		this.state = 1;
	}

	public void addUser(User user) {
		userList.add(user);
	}

	public void updateTitle(String title) {
		this.title = title;
	}

	public void updateOwner(User owner) {
		this.owner = owner;
	}
}
