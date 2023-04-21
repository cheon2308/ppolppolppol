package com.ppol.message.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ppol.message.entity.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
