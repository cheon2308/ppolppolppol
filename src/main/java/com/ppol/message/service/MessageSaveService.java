package com.ppol.message.service;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.ppol.message.document.mongodb.Message;
import com.ppol.message.document.mongodb.MessageUser;
import com.ppol.message.dto.request.MessageRequestDto;
import com.ppol.message.util.constatnt.classes.ValidationConstants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageSaveService {

	private final MongoTemplate mongoTemplate;
	private final RedisTemplate<String, Object> redisTemplate;
	private final MessageReadService messageReadService;
	private final UserAuthorizationService userAuthorizationService;
	private final UserReadService userReadService;

	// 채팅 메시지를 MongoDB에 저장하고 Redis에 메시지를 업데이트
	public void saveChatMessage(MessageRequestDto messageRequestDto, String accessToken, String messageChannelId) {

		Long userId = userAuthorizationService.getUserId(accessToken);

		Message message = createMessageMapping(messageRequestDto, userReadService.findUser(userId), messageChannelId);

		// MongoDB에 저장
		mongoTemplate.save(message);

		// Redis에서 해당 채팅방의 메시지 리스트를 가져옴
		List<Message> lastMessages = messageReadService.getLastMessages(messageChannelId);
		if (lastMessages.size() >= ValidationConstants.CACHE_SIZE) {
			// 메시지가 SIZE 이상이면 가장 오래된 메시지 삭제
			lastMessages.remove(0);
		}
		lastMessages.add(message); // 새로운 메시지 추가

		// Redis에 업데이트된 메시지 목록 저장
		redisTemplate.opsForValue().set(messageChannelId, lastMessages);
	}

	public Message createMessageMapping(MessageRequestDto messageRequestDto, MessageUser messageUser,
		String messageChannelId) {

		return Message.builder()
			.content(messageRequestDto.getContent())
			.messageChannelId(new ObjectId(messageChannelId))
			.sender(messageUser)
			.timestamp(LocalDateTime.now())
			.build();
	}
}
