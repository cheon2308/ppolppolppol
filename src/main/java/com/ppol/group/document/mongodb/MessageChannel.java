package com.ppol.group.document.mongodb;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ppol.group.entity.user.User;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 서비스의 채팅 메시지 채널을 나타내는 Document
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Document(collection = "message_channels")
public class MessageChannel implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	private ObjectId id;

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

	public void addUser(User user) {
		userList.add(MessageUser.of(user));
	}
}
