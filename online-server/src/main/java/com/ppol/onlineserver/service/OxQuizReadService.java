package com.ppol.onlineserver.service;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.ppol.onlineserver.dto.OxGameDto;
import com.ppol.onlineserver.entity.OxQuiz;
import com.ppol.onlineserver.repository.OxQuizRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * OX 문제를 불러오는 기능을 하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OxQuizReadService {

	// repository
	private final OxQuizRepository oxQuizRepository;

	/**
	 * 해당 게임에 대해 다음 OX 퀴즈를 불러오는 메서드, 해당 게임에서 이전에 나온적이 없는 문제를 찾도록 한다.
	 */
	public OxQuiz getOxQuiz(OxGameDto oxGame) {

		long count = oxQuizRepository.count();

		Random random = new Random();

		long quizId = random.nextLong(count);
		while(oxGame.getPreviousQuestions().contains(quizId)) {
			quizId = random.nextLong(count);
		}

		return getOxQuiz(quizId);
	}

	/**
	 * OX 퀴즈 엔티티 하나를 불러오는 메서드, 예외처리 포함
	 */
	public OxQuiz getOxQuiz(Long quizId) {
		return oxQuizRepository.findById(quizId).orElseThrow(() -> new EntityNotFoundException("OX 퀴즈"));
	}
}
