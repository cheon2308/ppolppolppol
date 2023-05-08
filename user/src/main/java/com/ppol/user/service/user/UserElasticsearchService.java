package com.ppol.user.service.user;

import org.springframework.stereotype.Service;

import com.ppol.user.document.User;
import com.ppol.user.repository.elasticsearch.UserElasticRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserElasticsearchService {

	// repository
	private final UserElasticRepository userElasticRepository;

	/**
	 *	Maria DB에 저장된 유저의 id값과 검색을 위한 username 정보를 가지는 document를 새로 생성하는 메서드
	 */
	public void saveUser(Long userId, String username) {
		userElasticRepository.save(User.builder().id(userId).username(username).build());
	}

	/**
	 * 유저 ID값으로 document를 삭제하는 메서드
	 */
	public void deleteUser(Long userId) {
		userElasticRepository.deleteById(userId);
	}
}
