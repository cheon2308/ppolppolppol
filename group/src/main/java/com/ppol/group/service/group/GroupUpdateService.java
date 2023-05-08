package com.ppol.group.service.group;

import org.springframework.stereotype.Service;

import com.ppol.group.dto.request.group.GroupUpdateDto;
import com.ppol.group.dto.response.group.GroupDetailDto;
import com.ppol.group.entity.group.Group;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 그룹의 기본 정보를 수정하는 기능들을 제공하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GroupUpdateService {

	// service
	private final GroupReadService groupReadService;

	/**
	 * 그룹의 기본 정보를 수정하는 메서드
	 */
	@Transactional
	public GroupDetailDto updateGroup(Long groupId, Long userId, GroupUpdateDto groupUpdateDto) {

		Group group = groupReadService.getGroupWithOwner(groupId, userId);

		group.updateTitle(groupUpdateDto.getTitle());

		return GroupDetailDto.of(group);
	}
}
