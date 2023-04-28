package com.ppol.search.service.article;

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

import com.ppol.search.document.Article;
import com.ppol.search.dto.response.ArticleListDto;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleSearchService {

	// service
	private final ArticleReadService articleReadService;

	// other
	private final ElasticsearchOperations operations;

	@Transactional
	public Slice<ArticleListDto> searchArticleByHashTag(Long userId, int page, int size, String keyword) {

		Pageable pageable = PageRequest.of(page, size);

		NativeQuery query = NativeQuery.builder()
			.withQuery(q -> q.term(t -> t.field("hashTags").value(keyword)))
			.withPageable(pageable)
			.build();

		SearchHits<Article> searchHits = operations.search(query, Article.class);

		return makeSlice(searchHits, pageable, userId);
	}

	@Transactional
	public Slice<ArticleListDto> searchArticleByContent(Long userId, int page, int size, String keyword) {

		Pageable pageable = PageRequest.of(page, size);

		NativeQuery query = NativeQuery.builder()
			.withQuery(q -> q.bool(b -> b.should(s -> s.match(match -> match.field("content").query(keyword)))))
			.withPageable(pageable)
			.withMinScore(5)
			.build();

		SearchHits<Article> searchHits = operations.search(query, Article.class);

		return makeSlice(searchHits, pageable, userId);
	}

	public Slice<ArticleListDto> makeSlice(SearchHits<Article> searchHits, Pageable pageable, Long userId) {

		List<Long> articleIdList = searchHits.getSearchHits()
			.stream()
			.map(SearchHit::getContent)
			.map(Article::getId)
			.toList();

		List<ArticleListDto> content = articleReadService.findArticleList(articleIdList, userId);

		return new SliceImpl<>(content, pageable, content.size() != 0);
	}
}
