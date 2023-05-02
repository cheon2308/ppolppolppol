package com.ppol.group.service.group;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ppol.group.dto.response.group.GroupDetailDto;
import com.ppol.group.dto.response.group.GroupListDto;
import com.ppol.group.entity.group.Group;
import com.ppol.group.exception.exception.ForbiddenException;
import com.ppol.group.repository.jpa.GroupRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 그룹을 정보를 불러오는 기능들을 제공하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GroupReadService {

	// repository
	private final GroupRepository groupRepository;

	/**
	 * 사용자가 포함된 그룹 목록을 불러오는 메서드
	 */
	@Transactional
	public List<GroupListDto> findGroupList(Long userId) {
		return groupRepository.findByUserList_Id(userId).stream().map(GroupListDto::of).toList();
	}

	/**
	 * 특정 그룹 상세정보를 불러오는 메서드
	 */
	@Transactional
	public GroupDetailDto findGroup(Long groupId, Long userId) {
		return GroupDetailDto.of(getGroupWithParticipant(groupId, userId));
	}

	/**
	 * 기본 그룹 Entity를 불러올 때 사용자가 그룹의 생성자인지 확인하는 로직이 추가된 메서드
	 */
	public Group getGroupWithOwner(Long groupId, Long userId) {
		Group group = getGroup(groupId);

		if (!group.getOwner().getId().equals(userId)) {
			throw new ForbiddenException("그룹");
		} else {
			return group;
		}
	}

	/**
	 * 기본 그룹 Entity를 불러올 때 사용자가 그룹에 참여하고 있는지를 확인하는 로직이 추가된 메서드
	 */
	public Group getGroupWithParticipant(Long groupId, Long userId) {
		Group group = getGroup(groupId);

		if (group.getUserList().stream().noneMatch(user -> user.getId().equals(userId))) {
			throw new ForbiddenException("그룹");
		} else {
			return group;
		}
	}

	/**
	 * 기본 그룹 Entity를 불러오는 메서드, 예외처리를 포함
	 */
	public Group getGroup(Long groupId) {
		return groupRepository.findById(groupId).orElseThrow(() -> new EntityNotFoundException("그룹"));
	}
}
