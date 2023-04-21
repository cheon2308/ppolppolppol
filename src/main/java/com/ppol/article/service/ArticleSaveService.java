package com.ppol.article.service;

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
import com.ppol.article.util.constatnt.classes.ArticleConstants;
import com.ppol.article.util.s3.S3Uploader;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleSaveService {

	// repositories
	private final ArticleRepository articleRepository;

	// services
	private final ArticleReadService articleReadService;
	private final ArticleElasticSaveService articleElasticSaveService;
	private final UserReadService userReadService;

	// others
	private final S3Uploader s3Uploader;

	// public
	@Transactional
	public ArticleDetailDto articleCreate(Long userId, ArticleCreateDto articleCreateDto) {

		log.info("{}", articleCreateDto);
		log.info(userId.toString());

		if (articleCreateDto.getImageList() != null
			&& articleCreateDto.getImageList().size() > ArticleConstants.MAX_ARTICLE_IMAGE_COUNT) {
			throw new ImageCountException();
		}

		Article article = articleSave(userId, articleCreateDto);

		articleElasticSaveService.createArticleElasticsearch(articleCreateDto, article.getId());

		return articleReadService.articleDetailMapping(article, userId);
	}

	// private
	private Article articleSave(Long userId, ArticleCreateDto articleCreateDto) {
		User user = userReadService.getUser(userId);

		return articleRepository.save(
			articleCreateMapping(articleCreateDto, user, getImageUrlList(articleCreateDto.getImageList())));
	}

	private Article articleCreateMapping(ArticleCreateDto articleCreateDto, User user, List<String> imageList) {

		return Article.builder()
			.content(articleCreateDto.getContent())
			.imageList(imageList)
			.writer(user)
			.openStatus(articleCreateDto.getOpenStatus())
			.build();
	}

	private List<String> getImageUrlList(List<MultipartFile> fileList) {
		return fileList == null ? new ArrayList<>() : fileList.stream().map(s3Uploader::upload).toList();
	}
}
