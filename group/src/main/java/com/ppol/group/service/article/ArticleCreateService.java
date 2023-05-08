package com.ppol.group.service.article;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ppol.group.dto.request.article.ArticleCreateDto;
import com.ppol.group.dto.response.article.ArticleDetailDto;
import com.ppol.group.entity.group.Group;
import com.ppol.group.entity.group.GroupArticle;
import com.ppol.group.entity.user.User;
import com.ppol.group.exception.exception.ImageCountException;
import com.ppol.group.repository.jpa.GroupArticleRepository;
import com.ppol.group.service.alarm.AlarmSendService;
import com.ppol.group.service.group.GroupReadService;
import com.ppol.group.service.user.UserReadService;
import com.ppol.group.util.constatnt.classes.ValidationConstants;
import com.ppol.group.util.s3.S3Uploader;

import jakarta.transaction.Transactional;
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

	// service
	private final GroupReadService groupReadService;
	private final UserReadService userReadService;
	private final AlarmSendService alarmSendService;

	// other
	private final S3Uploader s3Uploader;

	/**
	 * 새로운 그룹 게시글/피드를 생성하는 메서드
	 */
	@Transactional
	public ArticleDetailDto createArticle(Long groupId, Long userId, ArticleCreateDto articleCreateDto) {

		// 포함된 사진 파일 정보가 10개가 넘으면 예외 처리
		if (articleCreateDto.getImageList() != null
			&& articleCreateDto.getImageList().size() > ValidationConstants.ARTICLE_CONTENT_MAX_SIZE) {
			throw new ImageCountException();
		}

		return ArticleDetailDto.of(articleSave(userId, groupId, articleCreateDto));
	}

	/**
	 * userId를 이용해 {@link User}객체, groupId를 이용해 {@link Group} 객체를 얻고 {@link GroupArticle}객체를 저장하는 메서드
	 */
	private GroupArticle articleSave(Long userId, Long groupId, ArticleCreateDto articleCreateDto) {
		User user = userReadService.getUser(userId);
		Group group = groupReadService.getGroupWithParticipant(groupId, userId);

		GroupArticle article = articleCreateMapping(articleCreateDto, user, group, getImageUrlList(articleCreateDto.getImageList()));

		alarmSendService.createNewArticleAlarm(group, article);

		return articleRepository.save(article);
	}

	/**
	 * 각 정보들을 매핑해서 {@link GroupArticle} 객체를 생성하는 메서드
	 */
	private GroupArticle articleCreateMapping(ArticleCreateDto articleCreateDto, User user, Group group,
		List<String> imageList) {

		return GroupArticle.builder()
			.content(articleCreateDto.getContent())
			.imageList(imageList)
			.writer(user)
			.group(group)
			.build();
	}

	/**
	 * 각 파일을 S3서버에 저장하고 해당 경로 String들을 List로 반환하는 메서드
	 */
	private List<String> getImageUrlList(List<MultipartFile> fileList) {
		return fileList == null ? new ArrayList<>() : fileList.stream().map(s3Uploader::upload).toList();
	}
}
