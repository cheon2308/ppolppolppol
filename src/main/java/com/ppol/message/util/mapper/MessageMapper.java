package com.ppol.message.util.mapper;

import java.time.LocalDateTime;

import com.ppol.message.document.Message;
import com.ppol.message.dto.request.MessageRequestDto;
import com.ppol.message.dto.response.UserDto;

public class MessageMapper {

	public static Message toEntity(MessageRequestDto messageRequestDto, UserDto userDto, Long messageChannelId) {

		return Message.builder()
			.content(messageRequestDto.getContent())
			.messageChannelId(messageChannelId)
			.sender(userDto)
			.timestamp(LocalDateTime.now())
			.build();
	}
}
