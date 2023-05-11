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

	@MessageMapping("/entering/{groupId}")
	public void enter(@DestinationVariable Long groupId, @Payload EnterDto enterDto) {

		log.info("{}, {}", groupId, enterDto);

		CharacterDto returnObject = characterUpdateService.enterGroup(groupId, enterDto);

		messagingTemplate.convertAndSend(getDestinationTopic(groupId), returnObject);
	}

	@MessageMapping("/moving/{groupId}")
	public void move(@DestinationVariable Long groupId, @Payload MoveDto moveDto) {

		log.info("{}, {}", groupId, moveDto);

		CharacterDto returnObject = characterUpdateService.moveCharacter(groupId, moveDto);

		messagingTemplate.convertAndSend(getDestinationTopic(groupId), returnObject);
	}

	@MessageMapping("/type/{groupId}")
	public void update(@DestinationVariable Long groupId, @Payload TypeUpdateDto typeUpdateDto) {

		log.info("{}, {}", groupId, typeUpdateDto);

		CharacterDto returnObject = characterUpdateService.updateCharacter(groupId, typeUpdateDto);

		messagingTemplate.convertAndSend(getDestinationTopic(groupId), returnObject);
	}

	/**
	 * 구독 경로를 불러오는 메서드
	 */
	private String getDestinationTopic(Long groupId) {
		return "/pub/" + groupId;
	}
}
