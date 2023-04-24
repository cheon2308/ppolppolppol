package com.ppol.message.dto.response;

import java.time.LocalDateTime;
import java.util.Set;

import com.ppol.message.document.mongodb.MessageUser;

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
public class ChannelResponseDto {

	String channelId;

	// 	해당 채팅 채널에 포함되어 있는 사용자들
	private Set<MessageUser> userList;

	/*
		해당 채팅 채널이 그룹 채팅 채널인 경우 그룹의 ID 값을 가짐 (Maria DB의 Group의 key 값)
		1대1 채팅의 경우 null
 	*/
	private Long groupId;

	// 	해당 채팅방의 제목 (그룹의 경우 그룹의 제목, 1대1 채팅방의 경우 따로 없음)
	private String title;

	/*
	 	최근 메시지 발생 시간
	 	사용자에게 채팅 채널 목록을 줄 때 최근 메시지 발생 시간으로 정렬해서 보여주기 위함
 	*/
	private LocalDateTime lastMessageTimestamp;
}
