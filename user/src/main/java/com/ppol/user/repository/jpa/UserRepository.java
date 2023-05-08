package com.ppol.user.repository.jpa;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ppol.user.entity.user.User;
import com.ppol.user.util.constatnt.enums.Provider;

public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByAccountIdAndProvider(String accountId, Provider provider);

	boolean existsByUsername(String username);

	@Query("SELECT u FROM User u WHERE u.id IN "
		+ "(SELECT f.following.id FROM Follow f WHERE f.follower.id = :userId AND f.following.id != :userId) "
		+ "AND u.state = 0 AND (u.followerCount < :followerCount OR (u.followerCount = :followerCount AND u.id > :lastId)) "
		+ "ORDER BY u.followerCount DESC, u.id ASC")
	Slice<User> findByUserFollowing(@Param("userId") Long userId, @Param("followerCount") Integer followerCount,
		@Param("lastId") Long lastId, Pageable pageable);

	@Query("SELECT u FROM User u WHERE u.id IN "
		+ "(SELECT f.follower.id FROM Follow f WHERE f.following.id = :userId AND f.follower.id != :userId) "
		+ "AND u.state = 0 AND (u.followerCount < :followerCount OR (u.followerCount = :followerCount AND u.id > :lastId)) "
		+ "ORDER BY u.followerCount DESC, u.id ASC")
	Slice<User> findByUserFollower(@Param("userId") Long userId, @Param("followerCount") Integer followerCount,
		@Param("lastId") Long lastId, Pageable pageable);
}
