package com.ppol.group.service.article;

import org.springframework.stereotype.Service;

import com.ppol.group.entity.group.GroupArticle;
import com.ppol.group.util.s3.S3Uploader;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 그룹 게시글/피드를 삭제하는 기능을 제공하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleDeleteService {

	// service
	private final ArticleReadService articleReadService;

	// other
	private final S3Uploader s3Uploader;

	/**
	 * 그룹 게시글/피드를 삭제하는 메서드
	 */
	@Transactional
	public void deleteArticle(Long groupId, Long articleId, Long userId) {

		GroupArticle article = articleReadService.getArticleOnlyWriter(articleId, groupId, userId);

		article.getImageList().forEach(s3Uploader::deleteFile);
		article.delete();
	}
}
