package com.ppol.article.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ppol.article.entity.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
