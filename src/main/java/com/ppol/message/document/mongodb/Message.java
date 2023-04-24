package com.ppol.message.document.mongodb;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 서비스의 채팅 메시지를 나타내는 Document
 * 대량의 메시지처리를 예상하기 때문에 몽고디비를 사용하여 처리한다.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Document(collection = "messages")
public class Message implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	private ObjectId id;

	// 메시지 내용
	private String content;

	// 해당 메시지가 작성된 메시지 채널의 ID
	private ObjectId messageChannelId;

	// 해당 메시지를 보낸 사용자 정보
	private MessageUser sender;

	// 메시지 전송 시간
	private LocalDateTime timestamp;
}
