package com.ppol.onlineserver.util.web_socket;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.ppol.onlineserver.dto.response.CharacterDto;
import com.ppol.onlineserver.service.CharacterUpdateService;
import com.ppol.onlineserver.util.UserMap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

	// web socket
	private final SimpMessageSendingOperations messagingTemplate;

	// service
	private final CharacterUpdateService characterUpdateService;

	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		String sessionId = headerAccessor.getSessionId();

		Long groupId = UserMap.getGroupId(sessionId);
		Long userId = UserMap.getUserId(sessionId);

		CharacterDto character = characterUpdateService.leaveGroup(groupId, userId);

		log.info("{} is leave from {}", userId, groupId);
		log.info("{}", character);

		messagingTemplate.convertAndSend("/pub/" + groupId, character);
	}
}