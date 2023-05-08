package com.ppol.user.repository.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ppol.user.entity.user.Follow;

public interface FollowRepository extends JpaRepository<Follow, Long> {

	Optional<Follow> findByFollower_IdAndFollowing_Id(Long followerId, Long followingId);
}
