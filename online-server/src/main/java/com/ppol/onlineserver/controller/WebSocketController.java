package com.ppol.onlineserver.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

import com.ppol.onlineserver.dto.request.MoveDto;
import com.ppol.onlineserver.dto.request.TypeUpdateDto;
import com.ppol.onlineserver.dto.request.UserIdDto;
import com.ppol.onlineserver.dto.response.OxLobbyDto;
import com.ppol.onlineserver.service.CharacterUpdateService;
import com.ppol.onlineserver.service.LobbyService;
import com.ppol.onlineserver.service.OxGameService;
import com.ppol.onlineserver.util.response.WebSocketResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class WebSocketController {

	// web socket
	private final SimpMessageSendingOperations messagingTemplate;

	// service
	private final CharacterUpdateService characterUpdateService;

	private final LobbyService lobbyService;

	private final OxGameService oxGameService;

	/**
	 * 그룹 방
	 */

	// 그룹 방 참여
	@MessageMapping("/entering/{groupId}")
	public void enter(@DestinationVariable String groupId, @Payload UserIdDto userIdDto) {

		log.info("group entering : {}, {}", groupId, userIdDto);

		WebSocketResponse<?> returnObject = characterUpdateService.enterGroup(groupId, userIdDto);

		if(returnObject != null) {
			messagingTemplate.convertAndSend(getDestinationTopic(groupId), returnObject);
		}
	}

	// 그룹 방 이동
	@MessageMapping("/moving/{groupId}")
	public void move(@DestinationVariable String groupId, @Payload MoveDto moveDto) {

		log.info("group moving : {}, {}", groupId, moveDto);

		WebSocketResponse<?> returnObject = characterUpdateService.moveCharacter(groupId, moveDto);

		messagingTemplate.convertAndSend(getDestinationTopic(groupId), returnObject);
	}

	// 그룹 방 캐릭터 타입 변경
	@MessageMapping("/type/{groupId}")
	public void update(@DestinationVariable String groupId, @Payload TypeUpdateDto typeUpdateDto) {

		log.info("group type : {}, {}", groupId, typeUpdateDto);

		WebSocketResponse<?> returnObject = characterUpdateService.updateCharacter(groupId, typeUpdateDto);

		messagingTemplate.convertAndSend(getDestinationTopic(groupId), returnObject);
	}

	// 그룹 방 떠나기
	@MessageMapping("/leaving/{groupId}")
	public void leave(@DestinationVariable String groupId, @Payload UserIdDto userIdDto) {

		log.info("group leave : {}, {}", groupId, userIdDto);

		WebSocketResponse<?> returnObject = characterUpdateService.leaveGroup(groupId, userIdDto.getUserId());

		messagingTemplate.convertAndSend(getDestinationTopic(groupId), returnObject);
	}

	/**
	 * OX 퀴즈 방 시작 전 과정
	 */

	// ox 게임 방 생성
	@MessageMapping("/ox/making/{groupId}")
	public void makeOx(@DestinationVariable String groupId, @Payload OxLobbyDto oxLobbyDto) {

		log.info("ox lobby making : {}, {}", groupId, oxLobbyDto);

		WebSocketResponse<?> returnObject = lobbyService.makeOxLobby(oxLobbyDto.getUserId(), groupId, oxLobbyDto);

		messagingTemplate.convertAndSend(getDestinationTopic(groupId), returnObject);
	}

	// ox 게임 방 파토내기
	@MessageMapping("/ox/destroying/{groupId}")
	public void destroyOx(@DestinationVariable String groupId, @Payload UserIdDto userIdDto) {

		log.info("ox lobby making : {}, {}", groupId, userIdDto);

		WebSocketResponse<?> returnObject = lobbyService.destroyOxLobby(userIdDto.getUserId(), groupId);

		// ox 퀴즈 방 파토
		messagingTemplate.convertAndSend(getDestinationTopic(groupId), returnObject);
	}

	// ox 게임 로비에서 참여
	@MessageMapping("/ox/ready/{groupId}")
	public void readyOx(@DestinationVariable String groupId, @Payload UserIdDto userIdDto) {

		log.info("ox lobby ready : {}, {}", groupId, userIdDto);

		WebSocketResponse<?> returnObject = lobbyService.getEnterLobby(userIdDto.getUserId(), groupId);

		messagingTemplate.convertAndSend(getDestinationTopic(groupId), returnObject);
	}

	// ox 게임 로비에서 나가기
	@MessageMapping("/ox/cancellation-ready/{groupId}")
	public void leaveOx(@DestinationVariable String groupId, @Payload UserIdDto userIdDto) {

		log.info("ox lobby cancel : {}, {}", groupId, userIdDto);

		WebSocketResponse<?> returnObject = lobbyService.getLeaveLobby(userIdDto.getUserId(), groupId);

		messagingTemplate.convertAndSend(getDestinationTopic(groupId), returnObject);
	}

	// ox 게임 시작
	@MessageMapping("/ox/starting/{groupId}")
	public void startOx(@DestinationVariable String groupId, @Payload OxLobbyDto oxLobbyDto) {

		log.info("ox game start !! : {}, {}", groupId, oxLobbyDto);

		WebSocketResponse<?> returnObject = oxGameService.startGame(groupId, oxLobbyDto);

		messagingTemplate.convertAndSend(getDestinationTopic(groupId), returnObject);
	}

	/**
	 * OX 퀴즈 방 시작 후 과정
	 */

	// ox 정답 불러오기
	@MessageMapping("/ox/answer/{groupId}")
	public void answerOx(@DestinationVariable String groupId, @Payload MoveDto moveDto) {

		log.info("ox answer : {}, {}", groupId, moveDto);

		oxGameService.answerGame(groupId, moveDto);
	}

	/**
	 * 그룹 구독 경로를 불러오는 메서드
	 */
	private String getDestinationTopic(String channel) {
		return "/pub/" + channel;
	}
}
