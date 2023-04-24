package com.ppol.message.dto.response;

import java.time.format.DateTimeFormatter;

import com.ppol.message.document.mongodb.Message;
import com.ppol.message.document.mongodb.MessageUser;
import com.ppol.message.util.constatnt.classes.DateTimeFormatString;

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

	private String messageChannelId;

	private MessageUser sender;

	private String timestamp;

	public static MessageResponseDto of(Message message) {
		return MessageResponseDto.builder()
			.messageId(message.getId().toString())
			.content(message.getContent())
			.messageChannelId(message.getMessageChannelId().toString())
			.sender(message.getSender())
			.timestamp(message.getTimestamp().format(DateTimeFormatter.ofPattern(DateTimeFormatString.BASIC)))
			.build();
	}
}
