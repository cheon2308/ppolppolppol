package com.ppol.personal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ppol.personal.entity.personal.Album;

public interface AlbumRepository extends JpaRepository<Album, Long> {
}
