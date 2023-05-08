package com.ppol.group.service.comment;

import java.time.LocalDateTime;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.ppol.group.dto.response.CommentResponseDto;
import com.ppol.group.entity.group.GroupArticleComment;
import com.ppol.group.exception.exception.ForbiddenException;
import com.ppol.group.exception.exception.InvalidParameterException;
import com.ppol.group.repository.jpa.GroupArticleCommentRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 그룹 게시글/피드에 대한 댓글을 불러오는 기능들을 제공하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CommentReadService {

	// repository
	private final GroupArticleCommentRepository commentRepository;

	/**
	 * 특정 그룹 게시글/피드에 대한 댓글 목록을 Slice로 불러오는 메서드
	 */
	public Slice<CommentResponseDto> readCommentList(Long articleId, Long lastId, int size) {

		LocalDateTime timestamp = lastId == null ? LocalDateTime.now() : getComment(lastId).getCreatedAt();
		Pageable pageable = PageRequest.of(0, size);

		Slice<GroupArticleComment> slice = commentRepository.findByGroupArticle_IdAndCreatedAtBeforeOrderByCreatedAtDesc(
			articleId, timestamp, pageable);

		return slice.map(CommentResponseDto::of);
	}

	/**
	 * 특정 그룹 게시글/피드에 대한 대표 댓글 DTO를 반환하는 메서드
	 */
	public CommentResponseDto getArticlePresentComment(Long articleId, Long writerId) {

		GroupArticleComment comment = commentRepository.findTopByGroupArticle_IdAndWriter_IdOrderByCreatedAtDesc(
				articleId, writerId)
			.orElseGet(() -> commentRepository.findTopByGroupArticle_IdOrderByCreatedAtDesc(articleId).orElse(null));

		return comment == null ? null : CommentResponseDto.of(comment);
	}

	/**
	 * 특정 댓글 엔티티를 해당 댓글 작성자만 불러올 수 있도록 하는 메서드
	 */
	public GroupArticleComment getComment(Long commentId, Long groupId, Long articleId, Long userId) {
		GroupArticleComment comment = getComment(commentId, groupId, articleId);

		if(!comment.getWriter().getId().equals(userId)) {
			throw new ForbiddenException("그룹 게시글/피드의 댓글");
		} else {
			return comment;
		}
	}

	/**
	 * 댓글 엔티티를 불러 올 때 올바른 그룹 ID, 게시글 ID를 통해 호출했는지 확인하는 로직을 포함한다.
	 */
	private GroupArticleComment getComment(Long commentId, Long groupId, Long articleId) {
		GroupArticleComment comment = getComment(commentId);

		if (!comment.getGroupArticle().getId().equals(articleId) || !comment.getGroupArticle()
			.getGroup()
			.getId()
			.equals(groupId)) {
			throw new InvalidParameterException("그룹 게시글 댓글 불러오기 시 올바르지 않은 그룹 ID 혹은 게시글 ID를 통한 호출입니다.");
		} else {
			return comment;
		}
	}

	/**
	 * 기본 댓글 엔티티를 불러오는 메서드, 예외처리를 포함한다.
	 */
	private GroupArticleComment getComment(Long commentId) {
		return commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("그룹 게시글/피드 댓글"));
	}
}