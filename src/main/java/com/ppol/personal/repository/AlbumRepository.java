package com.ppol.personal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ppol.personal.entity.personal.Album;

public interface AlbumRepository extends JpaRepository<Album, Long> {

	List<Album> findByPersonalRoom_Id(Long roomId);

	int countByPersonalRoom_Id(Long roomId);
}
