package com.ppol.onlineserver.util.web_socket;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

import com.ppol.onlineserver.service.CharacterUpdateService;
import com.ppol.onlineserver.util.UserMap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomInterceptor implements ChannelInterceptor {

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);

		if (headerAccessor.getMessageType() == SimpMessageType.SUBSCRIBE) {

			// 연결 요청의 세션 아이디 불러오기
			String sessionId = headerAccessor.getSessionId();

			// 연결 요청의 구독 아이디 불러오기 (=userId)
			String subscriptionId = headerAccessor.getSubscriptionId();
			assert subscriptionId != null;
			Long userId = Long.parseLong(subscriptionId);

			// 연결 경로에 포함된 그룹 아이디 불러오기
			String destination = (String)message.getHeaders().get("simpDestination");
			assert destination != null;
			long groupId = Long.parseLong(destination.substring(5));

			// sessionId를 key로 userId, groupId를 등록
			UserMap.put(sessionId, userId, groupId);

			log.info("session id : {} and user id : {} is connected", sessionId, userId);
		}

		return message;
	}
}