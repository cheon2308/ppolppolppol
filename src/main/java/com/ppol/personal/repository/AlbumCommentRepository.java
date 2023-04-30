package com.ppol.personal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ppol.personal.entity.personal.AlbumComment;

public interface AlbumCommentRepository extends JpaRepository<AlbumComment, Long> {
}
