package com.ppol.message.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.ppol.message.dto.request.MessageRequestDto;
import com.ppol.message.service.message.MessageSaveService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class WebSocketMessageController {

	private final MessageSaveService messageSaveService;

	private final SimpMessageSendingOperations messagingTemplate;

	/**
	 * 메시지 전송 요청 처리
	 */
	@MessageMapping("/messages/{messageChannelId}")
	public void sendMessage(@RequestHeader(name = "Authorization") String accessToken,
		@DestinationVariable String messageChannelId, @Payload MessageRequestDto messageRequestDto) {

		messageSaveService.saveChatMessage(messageRequestDto, accessToken, messageChannelId); // MongoDB에 채팅 메시지 저장

		String destinationTopic = "/pub/chat/room/" + messageChannelId;

		messagingTemplate.convertAndSend(destinationTopic, messageRequestDto);
	}
}