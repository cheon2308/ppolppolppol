package com.ppol.message.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {

		// 클라이언트에서 메시지를 보낼 때 사용하는 destination 접두사 설정
		config.setApplicationDestinationPrefixes("/sub");

		// 클라이언트에서 메시지를 구독할 때 사용하는 destination 접두사 설정
		config.enableSimpleBroker("/pub");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// 웹소켓 엔드포인트 설정
		registry.addEndpoint("/ws-message").setAllowedOriginPatterns("*").withSockJS(); // SockJS 지원
	}
}
