package com.ppol.user.service.user;

import org.springframework.stereotype.Service;

import com.ppol.user.entity.user.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDeleteService {

	// service
	private final UserReadService userReadService;
	private final UserElasticsearchService userElasticsearchService;

	/**
	 * 사용자 정보를 삭제 처리
	 */
	@Transactional
	public void deleteUser(Long userId) {
		User user = userReadService.getUser(userId);

		user.delete();

		userElasticsearchService.deleteUser(userId);
	}
}
