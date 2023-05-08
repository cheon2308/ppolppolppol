package com.ppol.personal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ppol.personal.entity.personal.AlbumContent;

public interface AlbumContentRepository extends JpaRepository<AlbumContent, Long> {

	List<AlbumContent> findByAlbum_Id(Long albumId);

	Slice<AlbumContent> findByAlbum_IdAndOrderGreaterThanOrderByOrderAsc(Long albumId, Integer order, Pageable pageable);

	Optional<AlbumContent> findTopByAlbum_IdOrderByOrderDesc(Long albumId);
}
