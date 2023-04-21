package com.ppol.message.service;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.ppol.message.document.Message;
import com.ppol.message.dto.response.MessageResponseDto;
import com.ppol.message.util.constatnt.classes.MessageConstants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageReadService {

	private final MongoTemplate mongoTemplate;
	private final RedisTemplate<String, Object> redisTemplate;

	public Slice<MessageResponseDto> findLastMessages(Long messageChannelId) {

		Pageable pageable = PageRequest.of(0, MessageConstants.CACHE_SIZE);

		return new SliceImpl<>(getLastMessages(messageChannelId).stream().map(MessageResponseDto::of).toList(),
			pageable, true);
	}

	public Slice<MessageResponseDto> findPreviousMessages(Long messageChannelId, ObjectId messageId, int size) {

		Pageable pageable = PageRequest.of(0, size);

		List<MessageResponseDto> content = getPreviousMessages(messageChannelId, messageId, size).stream()
			.map(MessageResponseDto::of)
			.toList();

		boolean hasNext = content.size() != 0;

		return new SliceImpl<>(content, pageable, hasNext);
	}

	// Redis에서 해당 채팅방의 캐쉬 사이즈 만큼의 메시지를 가져옴
	public List<Message> getLastMessages(Long messageChannelId) {

		List<Message> lastMessages = (List<Message>) redisTemplate.opsForValue().get(messageChannelId);

		if (lastMessages == null) {
			lastMessages = new ArrayList<>();
		}

		return lastMessages;
	}

	// MongoDB에서 이전 메시지를 불러옴 (무한 스크롤 처리)
	public List<Message> getPreviousMessages(Long messageChannelId, ObjectId messageId, int size) {

		Query query = new Query();
		query.addCriteria(Criteria.where("messageChannelId").is(messageChannelId).and("_id").lt(messageId));
		query.with(Sort.by(Sort.Direction.DESC, "_id"));
		query.limit(size);

		return mongoTemplate.find(query, Message.class);
	}
}
