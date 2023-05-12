package com.ppol.onlineserver.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 사용자의 입장 정보를 담는 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserIdDto {

	@NotNull
	private Long userId;
}
