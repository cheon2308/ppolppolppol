package com.ppol.message.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.ppol.message.dto.request.MessageRequestDto;
import com.ppol.message.dto.response.MessageResponseDto;
import com.ppol.message.service.message.MessageSaveService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class WebSocketMessageController {

	private final MessageSaveService messageSaveService;

	private final SimpMessageSendingOperations messagingTemplate;

	/**
	 * 메시지 전송 요청 처리
	 */
	@MessageMapping("/messages/{messageChannelId}")
	public void sendMessage(
		@DestinationVariable String messageChannelId, @Payload MessageRequestDto messageRequestDto) {

		log.info("{}", messageChannelId);
		log.info("{}", messageRequestDto);

		MessageResponseDto returnObject = messageSaveService.createMessage(messageRequestDto,
			messageChannelId); // MongoDB에 채팅 메시지 저장

		String destinationTopic = "/pub/channels/" + messageChannelId;
		messagingTemplate.convertAndSend(destinationTopic, returnObject);
	}
}