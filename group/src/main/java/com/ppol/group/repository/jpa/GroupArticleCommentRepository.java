package com.ppol.group.repository.jpa;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ppol.group.entity.group.GroupArticleComment;

public interface GroupArticleCommentRepository extends JpaRepository<GroupArticleComment, Long> {

	Optional<GroupArticleComment> findTopByGroupArticle_IdAndWriter_IdOrderByCreatedAtDesc(Long articleId,
		Long writerId);

	Optional<GroupArticleComment> findTopByGroupArticle_IdOrderByCreatedAtDesc(Long articleId);

	Slice<GroupArticleComment> findByGroupArticle_IdAndCreatedAtBeforeOrderByCreatedAtDesc(Long articleId,
		LocalDateTime timestamp, Pageable pageable);
}
