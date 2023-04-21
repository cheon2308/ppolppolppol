package com.ppol.article.repository.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ppol.article.entity.article.ArticleCommentLike;

public interface ArticleCommentLikeRepository extends JpaRepository<ArticleCommentLike, Long> {

	Optional<ArticleCommentLike> findByArticleComment_IdAndUser_Id(Long commentId, Long userId);
}
