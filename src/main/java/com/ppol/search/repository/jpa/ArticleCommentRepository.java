package com.ppol.search.repository.jpa;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ppol.search.entity.article.ArticleComment;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {

	Optional<ArticleComment> findTopByArticle_IdAndWriter_IdOrderByLikeCountDesc(Long articleId, Long writerId);

	Optional<ArticleComment> findTopByArticle_IdOrderByLikeCountDesc(Long articleId);

	Optional<ArticleComment> findTopByArticle_IdAndParentAndWriter_IdOrderByLikeCountDesc(Long articleId, Long parent,
		Long writerId);

	Optional<ArticleComment> findTopByArticle_IdAndParentOrderByLikeCountDesc(Long articleId, Long parent);

	// 특정 게시글에 대한 댓글 목록 좋아요 순 정렬 불러오기
	@Query(
		"SELECT ac FROM ArticleComment ac WHERE ac.article.id = :articleId AND ac.parent = null AND (ac.likeCount < :likeCount "
			+ "OR (ac.likeCount = :likeCount AND ac.createdAt <= :createdAt AND ac.id != :id)) "
			+ "ORDER BY ac.likeCount DESC, ac.createdAt DESC")
	Slice<ArticleComment> findByArticleOrderByLike(@Param("articleId") Long articleId,
		@Param("likeCount") int likeCount, @Param("createdAt") LocalDateTime createdAt, @Param("id") Long id,
		Pageable pageable);

	// 특정 게시글에 대한 댓글 목록 최신 순 불러오기
	@Query("SELECT ac FROM ArticleComment ac WHERE ac.createdAt < :createdAt AND ac.parent = null "
		+ "AND ac.article.id = :articleId ORDER BY ac.createdAt DESC, ac.id DESC")
	Slice<ArticleComment> findByArticleOrderByCreatedAtDESC(@Param("articleId") Long articleId,
		@Param("createdAt") LocalDateTime createdAt, Pageable pageable);

	// 특정 게시글에 대한 댓글 목록 오래된 순 불러오기
	@Query("SELECT ac FROM ArticleComment ac WHERE ac.createdAt > :createdAt AND ac.parent = null "
		+ "AND ac.article.id = :articleId ORDER BY ac.createdAt ASC, ac.id DESC")
	Slice<ArticleComment> findByArticleOrderByCreatedAtASC(@Param("articleId") Long articleId,
		@Param("createdAt") LocalDateTime createdAt, Pageable pageable);

	// 특정 댓글에 대한 대댓글 목록 최신 순 불러오기
	@Query("SELECT ac FROM ArticleComment ac WHERE ac.createdAt > :createdAt AND ac.id != :id AND ac.parent = :parent "
		+ "AND ac.article.id = :articleId " + " ORDER BY ac.createdAt DESC")
	Slice<ArticleComment> findByParentOrderByCreatedAtDESC(@Param("articleId") Long articleId,
		@Param("createdAt") LocalDateTime createdAt, @Param("id") Long id, @Param("parent") Long parent,
		Pageable pageable);
}
