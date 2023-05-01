package com.ppol.group.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ppol.group.entity.group.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
