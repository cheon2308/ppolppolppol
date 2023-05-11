package com.ppol.onlineserver.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ppol.onlineserver.entity.UserCharacter;

public interface UserCharacterRepository extends JpaRepository<UserCharacter, Long> {

	Optional<UserCharacter> findByUser_Id(Long userId);
}
