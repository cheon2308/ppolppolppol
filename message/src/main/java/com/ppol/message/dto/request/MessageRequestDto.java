package com.ppol.message.dto.request;

import com.ppol.message.util.constatnt.ValidationConstants;
import com.ppol.message.util.constatnt.ValidationMessages;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class MessageRequestDto {

	@NotNull
	@Size(min = 1, max = ValidationConstants.MESSAGE_MAX_LENGTH, message = ValidationMessages.MESSAGE_SIZE_MSG)
	private String content;
}
