package com.ppol.search.repository.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ppol.search.entity.user.Follow;

public interface FollowRepository extends JpaRepository<Follow, Long> {

	Optional<Follow> findByFollower_IdAndFollowing_IdAndIsFollow(Long followerId, Long followingId, Boolean isFollow);
}
