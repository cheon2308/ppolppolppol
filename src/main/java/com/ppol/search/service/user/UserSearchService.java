package com.ppol.search.service.user;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import com.ppol.search.document.User;
import com.ppol.search.dto.response.UserResponseDto;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSearchService {

	// service
	private final UserReadService userReadService;

	// other
	private final ElasticsearchOperations operations;

	@Transactional
	public Slice<UserResponseDto> searchUser(Long userId, int page, int size, String keyword) {

		Pageable pageable = PageRequest.of(page, size);

		NativeQuery query = NativeQuery.builder().withQuery(q -> q.bool(b -> {
			b.mustNot(mn -> mn.match(match -> match.field("id").query(userId)));
			b.should(s -> s.match(match -> match.field("username").query(keyword)));
			return b;
		})).withPageable(pageable)
			.withMinScore(5)
			.build();

		SearchHits<User> searchHits = operations.search(query, User.class);

		List<Long> userIdList = searchHits.getSearchHits()
			.stream()
			.map(SearchHit::getContent)
			.map(User::getId)
			.toList();

		List<UserResponseDto> content = userReadService.readUserList(userIdList, userId);

		return new SliceImpl<>(content, pageable, content.size() != 0);
	}
}
