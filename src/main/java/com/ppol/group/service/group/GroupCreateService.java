package com.ppol.group.service.group;

import org.springframework.stereotype.Service;

import com.ppol.group.repository.jpa.GroupRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 그룹을 생성하는 기능들을 제공하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GroupCreateService {

	// repository
	private final GroupRepository groupRepository;

	// services
	private final GroupReadService groupReadService;
}
