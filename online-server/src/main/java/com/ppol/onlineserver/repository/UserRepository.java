package com.ppol.onlineserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ppol.onlineserver.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
