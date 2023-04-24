package com.ppol.article.service.comment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.ppol.article.dto.request.CommentCreateDto;
import com.ppol.article.dto.response.CommentDto;
import com.ppol.article.entity.article.Article;
import com.ppol.article.entity.article.ArticleComment;
import com.ppol.article.entity.user.User;
import com.ppol.article.repository.jpa.ArticleCommentRepository;
import com.ppol.article.service.user.UserReadService;
import com.ppol.article.service.article.ArticleReadService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 댓글 저장 기능을 담당하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CommentSaveService {

	// repositories
	private final ArticleCommentRepository commentRepository;

	// services
	private final ArticleReadService articleReadService;
	private final UserReadService userReadService;
	private final CommentReadService commentReadService;

	/**
	 * 댓글을 저장하는 메서드
	 */
	@Transactional
	public CommentDto createComment(Long articleId, CommentCreateDto commentCreateDto, Long userId) {

		Article article = articleReadService.getArticle(articleId);
		User user = userReadService.getUser(userId);
		List<Long> tagUserList = getTagUserList(commentCreateDto.getContent()).stream().map(User::getId).toList();

		ArticleComment comment = commentRepository.save(
				commentCreateMapping(commentCreateDto, article, user, tagUserList));

		return commentReadService.commentMapping(comment, userId);
	}

	/**
	 * 댓글의 내용에서 태그한 유저 목록을 가져오는 메서드
	 */
	private List<User> getTagUserList(String content) {

		List<String> usernames = new ArrayList<>();

		// Regular expression to match words starting with '@'
		Pattern pattern = Pattern.compile("@\\w+");
		Matcher matcher = pattern.matcher(content);

		// Iterate through all matches and add them to the list
		while (matcher.find()) {
			usernames.add(matcher.group().substring(1));
		}

		return usernames.stream().map(userReadService::getUser).filter(Objects::nonNull).toList();
	}

	private ArticleComment commentCreateMapping(CommentCreateDto commentCreateDto, Article article, User user,
		List<Long> tagUserList) {

		return ArticleComment.builder()
			.content(commentCreateDto.getContent())
			.parent(commentCreateDto.getParent())
			.article(article)
			.writer(user)
			.tagUserList(tagUserList)
			.build();
	}
}
