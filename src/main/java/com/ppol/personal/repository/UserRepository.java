package com.ppol.personal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ppol.personal.entity.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
