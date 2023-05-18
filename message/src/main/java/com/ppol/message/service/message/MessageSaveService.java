package com.ppol.message.service.message;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ppol.message.document.mongodb.Message;
import com.ppol.message.document.mongodb.MessageUser;
import com.ppol.message.dto.request.MessageRequestDto;
import com.ppol.message.dto.response.MessageResponseDto;
import com.ppol.message.repository.mongo.MessageRepository;
import com.ppol.message.service.user.UserReadService;
import com.ppol.message.util.constatnt.ValidationConstants;
import com.ppol.message.util.feign.AuthFeign;
import com.ppol.message.util.s3.S3Uploader;

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
	private final MessageSseService messageSseService;
	private final UserReadService userReadService;

	// others
	private final RedisTemplate<String, Object> redisTemplate;
	private final S3Uploader s3Uploader;
	private final AuthFeign authFeign;

	/**
	 * 채팅 메시지를 MongoDB에 저장하고 Redis에 메시지를 업데이트
	 */
	@Transactional
	public MessageResponseDto createMessage(MessageRequestDto messageRequestDto, String accessToken,
		String messageChannelId) {

		// 웹 소켓 컨트롤러에서는 AOP 사용이 안되서 직접 userId를 불러오는 부분을 호출한다.
		Long userId = authFeign.accessToken(accessToken);

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

		// SSE를 통해 채팅방 사용자들에게 새로운 메시지를 알림
		messageSseService.sendMessage(MessageResponseDto.of(message));

		return MessageResponseDto.of(message);
	}

	/**
	 * S3 서버에 메시지에 포함될 사진을 업로드하고 해당 사진의 경로를 반환하는 메서드
	 */
	@Transactional
	public String createMessageImage(MultipartFile image) {
		return s3Uploader.upload(image);
	}

	/**
	 * 받아온 메시지 DTO를 Document로 매핑하는 메서드
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
