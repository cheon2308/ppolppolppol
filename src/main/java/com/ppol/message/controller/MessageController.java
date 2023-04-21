package com.ppol.message.controller;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ppol.message.dto.response.MessageResponseDto;
import com.ppol.message.service.MessageReadService;
import com.ppol.message.util.response.ResponseBuilder;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

	private final MessageReadService messageReadService;

	@PostMapping("/images")
	public ResponseEntity<?> createMessageImage(MultipartFile image) {

		String url = "1234";

		return ResponseBuilder.created(url);
	}

	// 채팅방의 마지막 20개 메시지를 가져옴
	@GetMapping("/last-messages/{messageChannelId}")
	public ResponseEntity<?> findLastMessages(@PathVariable Long messageChannelId) {

		Slice<MessageResponseDto> returnObject = messageReadService.findLastMessages(messageChannelId);

		return ResponseBuilder.ok(returnObject);
	}

	// 이전 메시지를 불러옴 (무한 스크롤 처리)
	@GetMapping("/previous-messages/{messageChannelId}/{messageId}")
	public ResponseEntity<?> findPreviousMessages(@PathVariable Long messageChannelId, @PathVariable String messageId,
		@RequestParam int size) {

		Slice<MessageResponseDto> returnObject = messageReadService.findPreviousMessages(messageChannelId,
			new ObjectId(messageId), size);

		return ResponseBuilder.ok(returnObject);
	}
}
