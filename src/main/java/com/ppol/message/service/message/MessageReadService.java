package com.ppol.message.service.message;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.ppol.message.document.mongodb.Message;
import com.ppol.message.dto.response.MessageResponseDto;
import com.ppol.message.repository.mongo.MessageRepository;
import com.ppol.message.util.constatnt.ValidationConstants;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 채팅 메시지들을 불러오는 기능을 하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MessageReadService {

	// repository
	private final MessageRepository messageRepository;

	// others
	private final RedisTemplate<String, Object> redisTemplate;

	/**
	 * Redis에 저장된 특정 채팅방의 최근 메시지들을 불러오는 메서드
	 */
	public Slice<MessageResponseDto> findLastMessages(String messageChannelId) {

		Pageable pageable = PageRequest.of(0, ValidationConstants.CACHE_SIZE);

		List<MessageResponseDto> content = getLastMessages(messageChannelId).stream()
			.map(MessageResponseDto::of)
			.toList();

		return new SliceImpl<>(content, pageable, content.size() == ValidationConstants.CACHE_SIZE);
	}

	/**
	 * Mongo 디비로 부터 특정 채팅방의 이전 메시지들을 size 만큼 불러오는 메서드
	 */
	public Slice<MessageResponseDto> findPreviousMessages(String messageChannelId, String messageId, int size) {

		Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "timestamp"));

		Message message = messageId == null ? null : getMessage(new ObjectId(messageId));

		Slice<Message> slice = message != null ?
			messageRepository.findByMessageChannelIdAndTimestampAfterOrderByTimestampDesc(
				new ObjectId(messageChannelId), message.getTimestamp(), pageable) :
			messageRepository.findByMessageChannelIdOrderByTimestampDesc(new ObjectId(messageChannelId), pageable);

		List<MessageResponseDto> content = slice.stream().map(MessageResponseDto::of).toList();

		return new SliceImpl<>(content, pageable, slice.hasNext());
	}

	/**
	 * Redis에서 메시지 채널에 해당하는 메시지들을 불러오고 없다면 새로운 리스트를 반환하도록
	 */
	public List<Message> getLastMessages(String messageChannelId) {

		List<Message> lastMessages = (List<Message>)redisTemplate.opsForValue().get(messageChannelId);

		if (lastMessages == null) {
			lastMessages = new ArrayList<>();
		}

		log.info("{}", lastMessages);

		return lastMessages;
	}

	/**
	 * 기본 Document를 가져오는 메서드, 예외처리를 포함한다.
	 */
	public Message getMessage(ObjectId messageId) {
		return messageRepository.findById(messageId).orElseThrow(() -> new EntityNotFoundException("채팅 메시지"));
	}

	/**
	 * 특정 채팅방의 최신 메시지를 가져오는 메서드, 예외처리를 포함한다.
	 */
	public Message getNewMessage(ObjectId channelId) {
		return messageRepository.findTopByMessageChannelIdOrderByTimestampDesc(channelId).orElse(null);
	}

	/**
	 * 특정 채팅방에서 특정 메시지 이후로 작성된 메시지 숫자를 가져오는 메서드
	 */
	public int getNewMessageCount(ObjectId channelId, ObjectId messageId) {

		Message message = messageId == null ? null : getMessage(messageId);

		return message == null ? messageRepository.countByMessageChannelId(channelId) :
			messageRepository.countByMessageChannelIdAndTimestampAfter(channelId, message.getTimestamp());
	}
}
