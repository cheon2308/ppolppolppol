package com.ppol.article.service.comment;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;

import com.ppol.article.dto.response.CommentResponseDto;
import com.ppol.article.dto.response.UserResponseDto;
import com.ppol.article.entity.article.ArticleComment;
import com.ppol.article.exception.exception.ForbiddenException;
import com.ppol.article.repository.jpa.ArticleCommentRepository;
import com.ppol.article.service.user.UserInteractionReadService;
import com.ppol.article.util.DateTimeUtils;
import com.ppol.article.util.constatnt.enums.CommentOrder;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 댓글 불러오기 기능을 담당하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CommentReadService {

	// repositories
	private final ArticleCommentRepository commentRepository;

	// services
	private final UserInteractionReadService userInteractionReadService;

	// others

	/**
	 * 특정 게시글에 대해 댓글들을 불러오는 메서드
	 */
	public Slice<CommentResponseDto> findCommentList(Long articleId, Long lastCommentId, int size,
		CommentOrder commentOrder,
		Long userId) {

		ArticleComment lastComment = lastCommentId == null ? null : getArticleComment(lastCommentId);

		int likeCount = lastComment == null ? Integer.MAX_VALUE : lastComment.getLikeCount();
		LocalDateTime timestamp =
			lastComment == null ? (commentOrder == CommentOrder.NEW ? LocalDateTime.now() : LocalDateTime.MIN) :
				lastComment.getCreatedAt();
		articleId = articleId == null ? -1 : articleId;
		Pageable pageable = PageRequest.of(0, size);

		Slice<ArticleComment> slice = switch (commentOrder) {
			case LIKE ->
				commentRepository.findByArticleOrderByLike(articleId, likeCount, timestamp, lastCommentId, pageable);
			case NEW -> commentRepository.findByArticleOrderByCreatedAtDESC(articleId, timestamp, pageable);
			case OLD -> commentRepository.findByArticleOrderByCreatedAtASC(articleId, timestamp, pageable);
		};

		return slice.map(comment -> commentMappingWithPresent(comment, userId));
	}

	/**
	 * 특정 댓글의 대댓글들을 불러오는 메서드
	 */
	public Slice<CommentResponseDto> findReplyList(Long articleId, Long parent, int size, Long lastCommentId,
		Long userId) {

		ArticleComment lastComment = getArticleComment(lastCommentId);

		LocalDateTime timestamp = lastComment.getCreatedAt();
		Pageable pageable = PageRequest.of(0, size);

		Slice<ArticleComment> slice = commentRepository.findByParentOrderByCreatedAtDESC(articleId, timestamp,
			lastCommentId, parent, pageable);

		List<CommentResponseDto> content = slice.stream().map((comment -> commentMapping(comment, userId))).toList();

		return new SliceImpl<>(content, slice.getPageable(), slice.hasNext());
	}

	/**
	 * 특정 게시글에 대해 대표 댓글을 불러오는 메서드
	 * 대표 댓글의 우선순위 : 1순위 게시글 작성자의 댓글 중 좋아요 순서, 2순위 좋아요 순서
	 */
	public CommentResponseDto getArticlePresentComment(Long articleId, Long writerId, Long userId) {

		ArticleComment comment = commentRepository.findTopByArticle_IdAndWriter_IdOrderByLikeCountDesc(articleId,
			writerId).orElse(null);

		comment = comment != null ? comment :
			commentRepository.findTopByArticle_IdOrderByLikeCountDesc(articleId).orElse(null);

		return comment == null ? null : commentMapping(comment, userId);
	}

	/**
	 * 특정 댓글에 대해 대표 댓글을 불러오는 메서드
	 * 대표 댓글의 우선순위는 위와 같음
	 */
	public CommentResponseDto getCommentPresentComment(Long articleId, Long parent, Long writerId, Long userId) {

		ArticleComment comment = commentRepository.findTopByArticle_IdAndParentAndWriter_IdOrderByLikeCountDesc(
			articleId, parent, writerId).orElse(null);

		comment = comment != null ? comment :
			commentRepository.findTopByArticle_IdAndParentOrderByLikeCountDesc(articleId, parent).orElse(null);

		return comment == null ? null : commentMapping(comment, userId);
	}

	/**
	 * DB로 부터 댓글 엔티티를 불러오는 메서드, 예외처리를 포함한다.
	 */
	public ArticleComment getArticleComment(Long commentId) {
		return commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("댓글"));
	}

	/**
	 * 댓글 수정, 삭제 시 권한 체크를 포함하고 엔티티를 불러오는 메서드
	 */
	public ArticleComment getArticleCommentWithAuth(Long commentId, Long userId) {
		ArticleComment comment = getArticleComment(commentId);

		if (!comment.getWriter().getId().equals(userId)) {
			throw new ForbiddenException("댓글 수정/삭제");
		}

		return comment;
	}

	/**
	 * 댓글에 대표댓글 정보를 포함해서 DTO로 매핑하는 메서드
	 */
	public CommentResponseDto commentMappingWithPresent(ArticleComment comment, Long userId) {

		CommentResponseDto commentResponseDto = commentMapping(comment, userId);
		commentResponseDto.setComment(
			getCommentPresentComment(comment.getArticle().getId(), comment.getId(), comment.getWriter().getId(),
				userId));

		return commentResponseDto;
	}

	/**
	 * 댓글에 엔티티를 DTO로 매핑하는 메서드
	 */
	public CommentResponseDto commentMapping(ArticleComment comment, Long userId) {

		return CommentResponseDto.builder()
			.commentId(comment.getId())
			.articleId(comment.getArticle().getId())
			.writer(UserResponseDto.of(comment.getWriter()))
			.content(comment.getContent())
			.parent(comment.getParent())
			.isLike(userInteractionReadService.getArticleCommentLike(userId, comment.getId()))
			.createString(DateTimeUtils.getString(comment.getCreatedAt()))
			.createdAt(comment.getCreatedAt())
			.build();
	}
}
