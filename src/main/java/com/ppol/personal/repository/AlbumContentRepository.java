package com.ppol.personal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ppol.personal.entity.personal.AlbumContent;

public interface AlbumContentRepository extends JpaRepository<AlbumContent, Long> {

	List<AlbumContent> findByAlbum_Id(Long albumId);
}
