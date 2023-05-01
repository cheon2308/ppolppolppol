package com.ppol.group.service.group;

import org.springframework.stereotype.Service;

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
}
