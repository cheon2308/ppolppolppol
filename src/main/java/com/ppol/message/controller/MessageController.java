package com.ppol.message.controller;

import java.util.List;

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

import com.ppol.message.dto.response.ChannelResponseDto;
import com.ppol.message.dto.response.MessageResponseDto;
import com.ppol.message.service.ChannelReadService;
import com.ppol.message.service.MessageReadService;
import com.ppol.message.util.request.RequestUtils;
import com.ppol.message.util.response.ResponseBuilder;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

	// messages
	private final MessageReadService messageReadService;

	// channels
	private final ChannelReadService channelReadService;

	// 채팅방의 마지막 N개의 메시지를 가져옴
	@GetMapping("/last-messages/{messageChannelId}")
	public ResponseEntity<?> readLastMessages(@PathVariable String messageChannelId) {

		Slice<MessageResponseDto> returnObject = messageReadService.findLastMessages(messageChannelId);

		return ResponseBuilder.ok(returnObject);
	}

	// 이전 메시지를 불러옴 (무한 스크롤 처리)
	@GetMapping("/previous-messages/{messageChannelId}/{messageId}")
	public ResponseEntity<?> readPreviousMessages(@PathVariable String messageChannelId, @PathVariable String messageId,
		@RequestParam int size) {

		Slice<MessageResponseDto> returnObject = messageReadService.findPreviousMessages(messageChannelId,
			new ObjectId(messageId), size);

		return ResponseBuilder.ok(returnObject);
	}

	// 사용자가 참여중인 채팅방 목록을 불러옴
	@GetMapping("/channels")
	public ResponseEntity<?> readChannelList() {

		Long userId = RequestUtils.getUserId();
		List<ChannelResponseDto> returnObject = channelReadService.channelListRead(userId);

		return ResponseBuilder.ok(returnObject);
	}

	// 1대1 채팅방 정보를 불러옴
	@GetMapping("/direct-channels/{receiver}")
	public ResponseEntity<?> readDirectMessageChannel(@PathVariable Long receiver) {

		Long userId = RequestUtils.getUserId();
		ChannelResponseDto returnObject = channelReadService.directChannelRead(userId, receiver);

		return ResponseBuilder.ok(returnObject);
	}

	// 그룹 채팅방 정보를 불러옴
	@GetMapping("/group-channels/{groupId}")
	public ResponseEntity<?> readGroupMessageChannel(@PathVariable Long groupId) {

		Long userId = RequestUtils.getUserId();
		ChannelResponseDto returnObject = channelReadService.groupChannelRead(userId, groupId);

		return ResponseBuilder.ok(returnObject);
	}

	// 메시지에 사진을 추가하기 위해 저장
	@PostMapping("/channels/{channelId}/images")
	public ResponseEntity<?> createMessageImage(@PathVariable String channelId, MultipartFile image) {

		String url = "1234";

		return ResponseBuilder.created(url);
	}
}
