package com.ppol.personal.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ppol.personal.entity.personal.AlbumComment;

public interface AlbumCommentRepository extends JpaRepository<AlbumComment, Long> {

	Slice<AlbumComment> findByAlbum_IdAndCreatedAtBeforeOrderByCreatedAtDesc(Long albumId, LocalDateTime timestamp,
		Pageable pageable);
}
