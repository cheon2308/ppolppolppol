package com.ppol.personal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ppol.personal.entity.user.UserCharacter;

public interface UserCharacterRepository extends JpaRepository<UserCharacter, Long> {

	Optional<UserCharacter> findByUser_Id(Long userId);

	@Query("SELECT uc FROM UserCharacter uc " +
		"INNER JOIN Follow f ON uc.user.id = f.following.id " +
		"WHERE f.follower.id = :userId AND f.isFollow = true and f.following.id != :userId")
	List<UserCharacter> findFollowingsCharacters(@Param("userId") Long userId);
}
