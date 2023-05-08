package com.ppol.alarm.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ppol.alarm.entity.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
