package com.ppol.search.repository.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ppol.search.entity.article.ArticleLike;

public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {

	Optional<ArticleLike> findByArticle_IdAndUser_Id(Long articleId, Long userId);
}
