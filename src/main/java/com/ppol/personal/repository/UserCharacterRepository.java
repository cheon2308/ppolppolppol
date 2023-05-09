package com.ppol.personal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ppol.personal.entity.user.UserCharacter;

public interface UserCharacterRepository extends JpaRepository<UserCharacter, Long> {

	Optional<UserCharacter> findByUser_Id(Long userId);
}
