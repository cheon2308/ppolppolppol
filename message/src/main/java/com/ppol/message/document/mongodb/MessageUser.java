package com.ppol.message.document.mongodb;

import java.io.Serial;
import java.io.Serializable;

import org.bson.types.ObjectId;

import com.ppol.message.entity.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 	mongodb 메시지와 메시지 채널 Document에서 사용자 정보를 저장하기 위한 Serializable DTO
 * 	사용자의 간단한 기본 정보만을 저장하도록 한다.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MessageUser implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	// 사용자 ID (DB의 key 값)
	private Long userId;

	// 사용자의 username
	private String username;

	// 사용자의 프로필 이미지
	private String profileImage;

	// 해당 채팅방에서 해당 사용자가 읽은 가장 최근의 메시지의 ID
	private ObjectId lastReadMessageId;

	public void updateLastReadMessage(ObjectId messageId) {
		this.lastReadMessageId = messageId;
	}

	public static MessageUser of(User user) {
		return MessageUser.builder()
			.userId(user.getId())
			.username(user.getUsername())
			.profileImage(user.getImage())
			.build();
	}
}
