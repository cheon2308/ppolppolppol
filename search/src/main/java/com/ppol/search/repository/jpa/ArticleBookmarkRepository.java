package com.ppol.search.repository.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ppol.search.entity.article.ArticleBookmark;

public interface ArticleBookmarkRepository extends JpaRepository<ArticleBookmark, Long> {

	Optional<ArticleBookmark> findByArticle_IdAndUser_Id(Long articleId, Long userId);
}
