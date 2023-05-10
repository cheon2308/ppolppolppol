package com.ppol.message.service.user;

import org.springframework.stereotype.Service;

import com.ppol.message.document.mongodb.MessageUser;
import com.ppol.message.entity.user.User;
import com.ppol.message.repository.jpa.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserReadService {

	private final UserRepository userRepository;

	public MessageUser findUser(Long userId) {
		return MessageUser.of(getUser(userId));
	}

	public User getUser(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("사용자"));
	}
}
