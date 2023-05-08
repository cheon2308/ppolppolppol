package com.ppol.group.service.group;

import org.springframework.stereotype.Service;

import com.ppol.group.entity.group.Group;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 그룹을 삭제하는 기능들을 제공하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GroupDeleteService {

	// service
	private final GroupReadService groupReadService;

	/**
	 * 그룹을 삭제하는 메서드
	 */
	@Transactional
	public void deleteGroup(Long groupId, Long userId) {

		Group group = groupReadService.getGroupWithOwner(groupId, userId);

		group.delete();
	}
}
