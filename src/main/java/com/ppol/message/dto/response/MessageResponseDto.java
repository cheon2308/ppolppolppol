package com.ppol.message.dto.response;

import java.time.format.DateTimeFormatter;

import com.ppol.message.document.Message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MessageResponseDto {

	private String messageId;

	private String content;

	private Long messageChannelId;

	private UserDto sender;

	private String timestamp;

	public static MessageResponseDto of(Message message) {
		return MessageResponseDto.builder()
			.messageId(message.getId().toString())
			.content(message.getContent())
			.messageChannelId(message.getMessageChannelId())
			.sender(message.getSender())
			.timestamp(message.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
			.build();
	}
}
