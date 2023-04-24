package com.ppol.article.service.article;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ppol.article.dto.request.ArticleCreateDto;
import com.ppol.article.dto.response.ArticleDetailDto;
import com.ppol.article.entity.article.Article;
import com.ppol.article.entity.user.User;
import com.ppol.article.exception.exception.ImageCountException;
import com.ppol.article.repository.jpa.ArticleRepository;
import com.ppol.article.service.other.ArticleElasticService;
import com.ppol.article.service.user.UserReadService;
import com.ppol.article.util.constatnt.classes.ValidationConstants;
import com.ppol.article.util.s3.S3Uploader;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 게시글 저장 기능을 담당하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleSaveService {

	// repositories
	private final ArticleRepository articleRepository;

	// services
	private final ArticleReadService articleReadService;
	private final ArticleElasticService articleElasticService;
	private final UserReadService userReadService;

	// others
	private final S3Uploader s3Uploader;

	/**
	 * {@link ArticleCreateDto} 정보를 통해 MariaDB와 Elasticsearch에 게시글 정보를 저장한다.
	 */
	@Transactional
	public ArticleDetailDto articleCreate(Long userId, ArticleCreateDto articleCreateDto) {

		// 포함된 사진 파일 정보가 10개가 넘으면 예외 처리
		if (articleCreateDto.getImageList() != null
			&& articleCreateDto.getImageList().size() > ValidationConstants.ARTICLE_IMAGE_MAX_COUNT) {
			throw new ImageCountException();
		}

		Article article = articleSave(userId, articleCreateDto);

		articleElasticService.createArticleElasticsearch(articleCreateDto, article.getId());

		return articleReadService.articleDetailMapping(article, userId);
	}

	/**
	 * userId를 이용해 {@link User}객체를 얻고 {@link Article}객체를 저장하는 메서드
	 */
	private Article articleSave(Long userId, ArticleCreateDto articleCreateDto) {
		User user = userReadService.getUser(userId);

		return articleRepository.save(
			articleCreateMapping(articleCreateDto, user, getImageUrlList(articleCreateDto.getImageList())));
	}

	/**
	 * 각 정보들을 매핑해서 {@link Article} 객체를 생성하는 메서드
	 */
	private Article articleCreateMapping(ArticleCreateDto articleCreateDto, User user, List<String> imageList) {

		return Article.builder()
			.content(articleCreateDto.getContent())
			.imageList(imageList)
			.writer(user)
			.openStatus(articleCreateDto.getOpenStatus())
			.build();
	}

	/**
	 * 각 파일을 S3서버에 저장하고 해당 경로 String들을 List로 반환하는 메서드
	 */
	private List<String> getImageUrlList(List<MultipartFile> fileList) {
		return fileList == null ? new ArrayList<>() : fileList.stream().map(s3Uploader::upload).toList();
	}
}
