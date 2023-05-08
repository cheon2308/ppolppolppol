package com.ppol.article.repository.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ppol.article.entity.article.ArticleLike;

public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {

	Optional<ArticleLike> findByArticle_IdAndUser_Id(Long articleId, Long userId);
}
