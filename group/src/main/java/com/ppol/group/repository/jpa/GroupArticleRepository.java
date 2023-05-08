package com.ppol.group.repository.jpa;

import java.time.LocalDateTime;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ppol.group.entity.group.GroupArticle;

public interface GroupArticleRepository extends JpaRepository<GroupArticle, Long> {

	Slice<GroupArticle> findByGroup_IdOrderByFixedDescCreatedAtDesc(Long groupId, Pageable pageable);

	Slice<GroupArticle> findByGroup_IdAndCreatedAtBeforeAndFixedOrderByCreatedAt(Long groupId, LocalDateTime timestamp,
		boolean fixed, Pageable pageable);
}
