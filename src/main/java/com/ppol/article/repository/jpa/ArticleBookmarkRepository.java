package com.ppol.article.repository.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ppol.article.entity.article.ArticleBookmark;

public interface ArticleBookmarkRepository extends JpaRepository<ArticleBookmark, Long> {

	Optional<ArticleBookmark> findByArticle_IdAndUser_Id(Long articleId, Long userId);
}
