package com.ppol.group.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ppol.group.entity.group.GroupInvite;

public interface GroupInviteRepository extends JpaRepository<GroupInvite, Long> {

	Optional<GroupInvite> findByUser_IdAndGroup_Id(Long userId, Long groupId);
}
