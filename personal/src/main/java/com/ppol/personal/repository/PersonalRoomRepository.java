package com.ppol.personal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ppol.personal.entity.personal.PersonalRoom;

public interface PersonalRoomRepository extends JpaRepository<PersonalRoom, Long> {

	Optional<PersonalRoom> findByOwner_Id(Long userId);
}
