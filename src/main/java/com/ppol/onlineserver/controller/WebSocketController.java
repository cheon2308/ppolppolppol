package com.ppol.onlineserver.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

import com.ppol.onlineserver.dto.request.MoveRequestDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class WebSocketController {

	private final SimpMessageSendingOperations messagingTemplate;

	@MessageMapping("/moving/{groupId}")
	public void sendMessage(@DestinationVariable String groupId, @Payload MoveRequestDto moveRequestDto) {

		log.info(groupId);
		log.info("{}", moveRequestDto);

		String destinationTopic = "/pub/" + groupId;
		messagingTemplate.convertAndSend(destinationTopic, moveRequestDto);
	}
}
