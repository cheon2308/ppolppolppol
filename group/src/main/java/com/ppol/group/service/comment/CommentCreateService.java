package com.ppol.group.service.comment;

import org.springframework.stereotype.Service;

import com.ppol.group.dto.request.comment.CommentCreateDto;
import com.ppol.group.dto.response.CommentResponseDto;
import com.ppol.group.entity.group.GroupArticle;
import com.ppol.group.entity.group.GroupArticleComment;
import com.ppol.group.entity.user.User;
import com.ppol.group.repository.jpa.GroupArticleCommentRepository;
import com.ppol.group.service.article.ArticleReadService;
import com.ppol.group.service.user.UserReadService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 그룹 게시글/피드에 대한 댓글을 작성하는 기능들을 제공하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CommentCreateService {

	// repository
	private final GroupArticleCommentRepository commentRepository;

	// service
	private final ArticleReadService articleReadService;
	private final UserReadService userReadService;

	/**
	 * 그룹 게시글/피드에 대한 새로운 댓글을 생성하는 메서드
	 */
	@Transactional
	public CommentResponseDto createComment(Long groupId, Long articleId, Long userId,
		CommentCreateDto commentCreateDto) {

		GroupArticle article = articleReadService.getArticle(articleId, groupId, userId);
		User writer = userReadService.getUser(userId);

		GroupArticleComment comment = commentRepository.save(GroupArticleComment.builder()
			.content(commentCreateDto.getContent())
			.writer(writer)
			.groupArticle(article)
			.build());

		return CommentResponseDto.of(comment);
	}
}
