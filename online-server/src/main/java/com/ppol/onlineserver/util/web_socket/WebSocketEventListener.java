package com.ppol.onlineserver.util.web_socket;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.ppol.onlineserver.util.response.WebSocketResponse;
import com.ppol.onlineserver.service.CharacterUpdateService;
import com.ppol.onlineserver.util.UserUtil;

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

		Long groupId = UserUtil.getGroupId(sessionId);
		Long userId = UserUtil.getUserId(sessionId);

		WebSocketResponse<?> response = characterUpdateService.leaveGroup(groupId.toString(), userId);

		log.info("{} is leave from {}", userId, groupId);
		log.info("{}", response);

		if (UserUtil.delete(sessionId)) {
			log.info("Delete {} is success", sessionId);
		}

		messagingTemplate.convertAndSend("/pub/" + groupId, response);
	}
}