package com.ppol.onlineserver.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

import com.ppol.onlineserver.dto.request.EnterDto;
import com.ppol.onlineserver.dto.request.MoveDto;
import com.ppol.onlineserver.dto.request.TypeUpdateDto;
import com.ppol.onlineserver.dto.response.CharacterDto;
import com.ppol.onlineserver.service.CharacterUpdateService;

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

	/**
	 *	그룹 방
	 */

	// 그룹 방 참여
	@MessageMapping("/entering/{groupId}")
	public void enter(@DestinationVariable Long groupId, @Payload EnterDto enterDto) {

		log.info("group entering : {}, {}", groupId, enterDto);

		CharacterDto returnObject = characterUpdateService.enterGroup(groupId, enterDto);

		messagingTemplate.convertAndSend(getDestinationTopic(groupId), returnObject);
	}

	// 그룹 방 이동
	@MessageMapping("/moving/{groupId}")
	public void move(@DestinationVariable Long groupId, @Payload MoveDto moveDto) {

		log.info("group moving : {}, {}", groupId, moveDto);

		CharacterDto returnObject = characterUpdateService.moveCharacter(groupId, moveDto);

		messagingTemplate.convertAndSend(getDestinationTopic(groupId), returnObject);
	}

	// 그룹 방 캐릭터 타입 변경
	@MessageMapping("/type/{groupId}")
	public void update(@DestinationVariable Long groupId, @Payload TypeUpdateDto typeUpdateDto) {

		log.info("group type : {}, {}", groupId, typeUpdateDto);

		CharacterDto returnObject = characterUpdateService.updateCharacter(groupId, typeUpdateDto);

		messagingTemplate.convertAndSend(getDestinationTopic(groupId), returnObject);
	}

	/**
	 * 그룹 구독 경로를 불러오는 메서드
	 */
	private String getDestinationTopic(Long groupId) {
		return "/pub/" + groupId;
	}

	/**
	 * OX 퀴즈
	 */

	// ox 게임 방 생성
	@MessageMapping("/ox/making/{groupId}")
	public void makeOx(@DestinationVariable Long groupId, @Payload MoveDto moveDto) {

		log.info("ox making : {}, {}", groupId, moveDto);

		// ox 퀴즈 방에 대한 참여 여부를 물어보는 메시지를 보내기
		messagingTemplate.convertAndSend(getDestinationTopic(groupId), "");
	}

	// ox 게임 방 참여
	@MessageMapping("/ox/entering/{groupId}")
	public void enterOx(@DestinationVariable Long groupId, @Payload EnterDto enterDto) {

		log.info("ox entering : {}, {}", groupId, enterDto);

		messagingTemplate.convertAndSend(getOxDestinationTopic(groupId), "");
	}

	// ox 방 이동
	@MessageMapping("/ox/moving/{groupId}")
	public void moveOx(@DestinationVariable Long groupId, @Payload MoveDto moveDto) {

		log.info("ox moving : {}, {}", groupId, moveDto);

		CharacterDto returnObject = characterUpdateService.moveCharacter(groupId, moveDto);

		messagingTemplate.convertAndSend(getOxDestinationTopic(groupId), returnObject);
	}

	// ox 정답 불러오기
	@MessageMapping("/ox/answer/{groupId}")
	public void answerOx(@DestinationVariable Long groupId, @Payload MoveDto moveDto) {

		log.info("ox answer : {}, {}", groupId, moveDto);

		CharacterDto returnObject = characterUpdateService.moveCharacter(groupId, moveDto);

		messagingTemplate.convertAndSend(getOxDestinationTopic(groupId), returnObject);
	}

	/**
	 * ox 구독 경로를 불러오는 메서드
	 */
	private String getOxDestinationTopic(Long groupId) {
		return "/pub/ox/" + groupId;
	}
}
