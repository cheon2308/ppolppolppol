package com.ppol.article.repository.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ppol.article.entity.article.ArticleComment;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {

	Optional<ArticleComment> findTop1ByArticle_IdOrderByLikeCount(Long articleId);
}
