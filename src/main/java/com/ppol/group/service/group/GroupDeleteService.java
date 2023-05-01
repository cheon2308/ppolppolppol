package com.ppol.group.service.group;

import org.springframework.stereotype.Service;

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
}
