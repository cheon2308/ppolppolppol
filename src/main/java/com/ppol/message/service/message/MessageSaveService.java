package com.ppol.message.service.message;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ppol.message.document.mongodb.Message;
import com.ppol.message.document.mongodb.MessageUser;
import com.ppol.message.dto.request.MessageRequestDto;
import com.ppol.message.repository.mongo.MessageRepository;
import com.ppol.message.service.UserAuthService;
import com.ppol.message.service.UserReadService;
import com.ppol.message.util.constatnt.classes.ValidationConstants;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 채팅 메시지들을 불러오는 기능을 하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MessageSaveService {

	// repositories
	private final MessageRepository messageRepository;

	// services
	private final MessageReadService messageReadService;
	private final UserAuthService userAuthService;
	private final UserReadService userReadService;

	// others
	private final RedisTemplate<String, Object> redisTemplate;

	/**
	 * 채팅 메시지를 MongoDB에 저장하고 Redis에 메시지를 업데이트
	 */
	@Async
	@Transactional
	public void saveChatMessage(MessageRequestDto messageRequestDto, String accessToken, String messageChannelId) {

		// 웹 소켓 컨트롤러에서는 AOP 사용이 안되서 직접 userId를 불러오는 부분을 호출한다.
		Long userId = userAuthService.getUserId(accessToken);

		Message message = createMessageMapping(messageRequestDto, userReadService.findUser(userId), messageChannelId);

		// MongoDB에 저장
		messageRepository.save(message);

		// Redis에서 해당 채팅방의 메시지 리스트를 가져와서 캐쉬 크기 이상이면 가장 오래된 메시지를 삭제하고 새로운 메시지 추가하고 저장
		List<Message> lastMessages = messageReadService.getLastMessages(messageChannelId);
		if (lastMessages.size() >= ValidationConstants.CACHE_SIZE) {
			lastMessages.remove(0);
		}
		lastMessages.add(message);

		redisTemplate.opsForValue().set(messageChannelId, lastMessages);
	}

	/**
	 *	받아온 메시지 DTO를 Document로 매핑하는 메서드
	 */
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
