package com.ppol.message.document;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ppol.message.dto.response.UserDto;
import com.ppol.message.entity.global.BaseEntity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Document(collection = "messages")
public class Message extends BaseEntity implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	private ObjectId id;

	private String content;

	private Long messageChannelId;

	private UserDto sender;

	private LocalDateTime timestamp;
}
