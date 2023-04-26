package com.ppol.message.service.message;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.ppol.message.document.mongodb.MessageChannel;
import com.ppol.message.document.mongodb.MessageUser;
import com.ppol.message.dto.response.MessageResponseDto;
import com.ppol.message.repository.SseRepository;
import com.ppol.message.service.channel.ChannelReadService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 채팅 목록읇 보고 있는 사용자에게 새로운 메시지가 발생하면 이벤트를 통해 알리기 위한 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MessageSseService {

	// repositories
	private final SseRepository sseRepository;

	// services
	private final ChannelReadService channelReadService;

	// others

	/**
	 * 채팅 목록에 들어온 사용자가 SSE 구독, Emitter 등록
	 */
	@Transactional
	public SseEmitter subscribe(Long userId) {

		// emitter 객체 생성 및 저장
		SseEmitter emitter = sseRepository.save(userId);

		// emitter 만료되면 자동제거
		emitter.onCompletion(() -> sseRepository.deleteById(userId));
		emitter.onTimeout(() -> sseRepository.deleteById(userId));
		emitter.onError((e) -> sseRepository.deleteById(userId));

		// 503 에러를 방지하기 위한 더미 이벤트 전송
		sendToUser(userId, "dummy");

		return emitter;
	}

	/**
	 * 사용자에게 메시지를 받으면 해당 메시지 채널에 존재하는 사용자들에게 메시지를 보내기
	 */
	public void sendMessage(MessageResponseDto messageResponseDto) {

		MessageChannel channel = channelReadService.getMessageChannel(messageResponseDto.getMessageChannelId());

		channel.getUserList()
			.stream()
			.map(MessageUser::getUserId)
			.forEach(userId -> sendToUser(userId, messageResponseDto));
	}

	/**
	 * 사용자에게 메시지 정보 전송, 전송 실패 시 연결을 종료한것이므로 emitter 삭제
	 */
	private void sendToUser(Long userId, Object data) {

		SseEmitter emitter = sseRepository.get(userId);

		if (emitter != null) {
			try {
				emitter.send(SseEmitter.event().data(data));
			} catch (IOException exception) {
				sseRepository.deleteById(userId);
			}
		}
	}
}