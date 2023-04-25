package com.ppol.message.service.channel;

import org.bson.types.ObjectId;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ppol.message.document.mongodb.MessageChannel;
import com.ppol.message.document.mongodb.MessageUser;
import com.ppol.message.exception.exception.ForbiddenException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 채널에 포함된 유저의 정보를 업데이트하는 기능의 서비스
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ChannelUserUpdateService {

	// services
	private final ChannelReadService channelReadService;

	/**
	 * 사용자의 최근 읽은 메시지를 업데이트 하는 메서드
	 */
	@Async
	@Transactional
	public void updateUserLastReadMessage(String channelId, String messageId, Long userId) {

		MessageChannel channel = channelReadService.getMessageChannel(channelId);

		MessageUser messageUser = channel.getUserList()
			.stream()
			.filter(user -> user.getUserId().equals(userId))
			.findAny()
			.orElseThrow(() -> new ForbiddenException("채널"));

		messageUser.updateLastReadMessage(new ObjectId(messageId));
	}
}
