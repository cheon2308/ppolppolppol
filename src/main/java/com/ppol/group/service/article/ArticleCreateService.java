package com.ppol.group.service.article;

import org.springframework.stereotype.Service;

import com.ppol.group.repository.jpa.GroupArticleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 그룹 게시글/피드를 생성하는 기능들을 제공하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleCreateService {

	// repository
	private final GroupArticleRepository articleRepository;
}
