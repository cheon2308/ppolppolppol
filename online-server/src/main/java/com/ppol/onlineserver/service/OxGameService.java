package com.ppol.onlineserver.service;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import com.ppol.onlineserver.dto.OxGameDto;
import com.ppol.onlineserver.dto.OxGameKeyDto;
import com.ppol.onlineserver.dto.OxGameResponseDto;
import com.ppol.onlineserver.dto.OxGameUserDto;
import com.ppol.onlineserver.dto.request.MoveDto;
import com.ppol.onlineserver.dto.response.OxLobbyDto;
import com.ppol.onlineserver.entity.OxQuiz;
import com.ppol.onlineserver.util.OxGameUtil;
import com.ppol.onlineserver.util.OxLobbyUtil;
import com.ppol.onlineserver.util.constant.enums.EventType;
import com.ppol.onlineserver.util.response.WebSocketResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * OX 게임 진행에 따른 로직들을 처리하기 위한 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OxGameService {

	// service
	private final OxQuizReadService quizReadService;

	// web socket
	private final SimpMessageSendingOperations messagingTemplate;

	// other
	private final OxGameUtil gameUtil;
	private final TaskScheduler taskScheduler;
	private ScheduledFuture<?> future;

	/**
	 * OX 게임 최초 생성 메서드
	 */
	public WebSocketResponse<?> startGame(String groupId, OxLobbyDto oxLobbyDto) {

		Long userId = oxLobbyDto.getUserId();

		// 로비 맵에서 기존의 로비 지우기
		OxLobbyUtil.delete(groupId);

		String gameRoomId = gameUtil.makeNewGame(oxLobbyDto);

		return WebSocketResponse.of(userId, null, OxGameKeyDto.builder().gameKey(gameRoomId).build(),
			EventType.START_OX);
	}

	/**
	 * OX 게임에 참여하는 메서드
	 */
	public void enterGame(Long userId, String username, String gameRoomId) {
		int count = gameUtil.addPlayer(gameRoomId, OxGameUserDto.builder().userId(userId).username(username).build());

		if (count == 6) {
			scheduleTask(gameRoomId, 10);
		} else {
			rescheduleTask(gameRoomId);
		}
	}

	/**
	 * OX 퀴즈에 대한 정답 여부를 설정하고 다음 퀴즈를 내기 위한 메서드
	 */
	public void answerGame(String gameRoomId, MoveDto moveDto) {

		Boolean answer = moveDto.getX() >= 0;

		OxGameDto oxGame = gameUtil.getOxGame(gameRoomId);
		List<Long> questions = oxGame.getPreviousQuestions();
		OxQuiz oxQuiz = quizReadService.getOxQuiz(questions.get(questions.size() - 1));

		if (answer.equals(oxQuiz.getAnswer())) {
			OxGameUserDto oxGameUserDto = oxGame.getOxPlayers()
				.stream()
				.filter(oxGameUser -> oxGameUser.getUserId().equals(moveDto.getUserId()))
				.findAny()
				.orElseThrow();

			oxGameUserDto.setAnswerCount(oxGameUserDto.getAnswerCount() + 1);

			gameUtil.save(gameRoomId, oxGame);
		}

		rescheduleTask(gameRoomId);
	}

	/**
	 * 다음 OX 문제를 포함해서 {@link OxGameResponseDto}를 생성하는 메서드
	 */
	private WebSocketResponse<?> getOxGameResponse(String gameRoomId) {

		OxGameDto oxGame = gameUtil.getOxGame(gameRoomId);
		OxGameResponseDto oxGameResponseDto;
		EventType eventType;

		if (oxGame.getProblemNum() <= oxGame.getPreviousQuestions().size()) {

			// OX 퀴즈가 종료되었으므로 종료되었음을 알리는 메시지를 전송하고 레디스에서 삭제

			oxGameResponseDto = OxGameResponseDto.builder().oxGame(oxGame).build();

			eventType = EventType.END_OX;

			gameUtil.delete(gameRoomId);
		} else {
			OxQuiz oxQuiz = quizReadService.getOxQuiz(oxGame);

			oxGameResponseDto = OxGameResponseDto.builder()
				.oxGame(oxGame)
				.nextQuestion(oxQuiz.getQuestion())
				.commentary(oxQuiz.getCommentary())
				.answer(oxQuiz.getAnswer())
				.build();

			oxGame.getPreviousQuestions().add(oxQuiz.getId());

			gameUtil.save(gameRoomId, oxGame);

			eventType = EventType.NEXT_OX;
		}

		return WebSocketResponse.of(null, null, oxGameResponseDto, eventType);
	}

	private void sentOxGameResponse(String gameRoomId) {
		messagingTemplate.convertAndSend("/pub/" + gameRoomId, getOxGameResponse(gameRoomId));
	}

	private void scheduleTask(String gameRoomId, int second) {
		Instant startTime = Instant.now().plusSeconds(second);

		future = taskScheduler.schedule(() -> {
			log.info("{} 에서 다음 문제", gameRoomId);
			sentOxGameResponse(gameRoomId);
		}, startTime);
	}

	private void rescheduleTask(String gameRoomId) {
		if (future != null) {
			future.cancel(false);
		}

		scheduleTask(gameRoomId, 5);
	}
}
