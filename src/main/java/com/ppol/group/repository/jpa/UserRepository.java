package com.ppol.group.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ppol.group.entity.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
