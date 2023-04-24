package com.ppol.article.repository.jpa;

import java.time.LocalDateTime;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ppol.article.entity.article.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {

	@Query("SELECT a FROM Article a WHERE a.writer.id IN ("
		+ "SELECT f.following.id FROM Follow f WHERE f.follower.id = :userId) "
		+ "AND a.state = 0 AND a.createdAt <= :timestamp AND a.id != :articleId ORDER BY a.createdAt DESC")
	Slice<Article> findArticlesByFollowedUsers(@Param("userId") Long userId,
		@Param("timestamp") LocalDateTime timestamp, @Param("articleId") Long articleId, Pageable pageable);
}
