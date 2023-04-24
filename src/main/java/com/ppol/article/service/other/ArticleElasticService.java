package com.ppol.article.service.other;

import org.springframework.stereotype.Service;

import com.ppol.article.document.elastic.Article;
import com.ppol.article.dto.request.ArticleCreateDto;
import com.ppol.article.dto.request.ArticleUpdateDto;
import com.ppol.article.repository.elasticsearch.ArticleElasticRepository;
import com.ppol.article.util.constatnt.enums.OpenStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 게시글을 Elasticsearch에 저장, 삭제, 수정 기능을 담당하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleElasticService {

	// elasticsearch repositories
	private final ArticleElasticRepository articleElasticRepository;

	/**
	 * MariaDB에서의 해당 게시글의 아이디값으로 엘라스틱 서치에 게시글의 내용, 해쉬태그를 저장
	 */
	public void createArticleElasticsearch(ArticleCreateDto articleCreateDto, Long id) {

		if (articleCreateDto.getOpenStatus().equals(OpenStatus.PUBLIC)) {
			Article article = Article.builder()
				.id(id)
				.content(articleCreateDto.getContent())
				.hashTags(articleCreateDto.getHashTags())
				.build();

			articleElasticRepository.save(article);
		}
	}

	public void updateArticleElasticsearch(ArticleUpdateDto articleUpdateDto, Long id) {

		Article article = articleElasticRepository.findById(id).orElse(null);

		if (articleUpdateDto.getOpenStatus().equals(OpenStatus.PUBLIC)) {
			article = article == null ? Article.builder().id(id).build() : article;

			article.updateContent(articleUpdateDto.getContent());
			article.updateHashTags(articleUpdateDto.getHashTags());

			articleElasticRepository.save(article);
		} else if (article != null) {
			articleElasticRepository.deleteById(id);
		}
	}

	public void deleteArticleElasticsearch(Long id) {
		articleElasticRepository.deleteById(id);
	}
}
