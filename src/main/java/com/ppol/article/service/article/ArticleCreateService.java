package com.ppol.article.service.article;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ppol.article.dto.request.ArticleCreateDto;
import com.ppol.article.dto.response.ArticleDetailDto;
import com.ppol.article.entity.article.Article;
import com.ppol.article.entity.user.User;
import com.ppol.article.exception.exception.ImageCountException;
import com.ppol.article.repository.jpa.ArticleRepository;
import com.ppol.article.service.alarm.AlarmSendService;
import com.ppol.article.service.user.UserReadService;
import com.ppol.article.util.constatnt.classes.ValidationConstants;
import com.ppol.article.util.constatnt.enums.OpenStatus;
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
public class ArticleCreateService {

	// repositories
	private final ArticleRepository articleRepository;

	// services
	private final ArticleReadService articleReadService;
	private final ArticleElasticService articleElasticService;
	private final UserReadService userReadService;
	private final AlarmSendService alarmSendService;

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

		// 내용을 파싱해서 해쉬태그를 뽑아내기
		articleCreateDto.setHashTags(getHashTags(articleCreateDto.getContent()));

		// 검색을 위해 게시글 내용 정보와, 해쉬태그 정보들을 엘라스틱 서치에 저장
		articleElasticService.createArticleElasticsearch(articleCreateDto, article.getId());

		// 해당 유저를 팔로우하고 알람을 켜놓은 사람들에게 알람 생성 (비동기 처리)
		alarmSendService.makeFollowingNewArticleAlarm(article);

		return articleReadService.articleDetailMapping(article, userId);
	}

	/**
	 * 게시글의 내용중에서 @으로 시작하는 부분을 찾아서 해쉬태그로 뽑아내는 메서드
	 */
	public List<String> getHashTags(String content) {

		List<String> hashTagList = new ArrayList<>();

		// #로 시작하는 단어들을 찾아서 리스트로 리턴
		Pattern pattern = Pattern.compile("#\\S+");
		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			hashTagList.add(matcher.group().substring(1));
		}

		return hashTagList;
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
			.openStatus(OpenStatus.from(articleCreateDto.getOpenStatus()))
			.build();
	}

	/**
	 * 각 파일을 S3서버에 저장하고 해당 경로 String들을 List로 반환하는 메서드
	 */
	private List<String> getImageUrlList(List<MultipartFile> fileList) {
		return fileList == null ? new ArrayList<>() : fileList.stream().map(s3Uploader::upload).toList();
	}
}
