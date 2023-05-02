package com.ppol.group.dto.request.invite;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class InviteAnswerDto {

	@NotNull
	private Boolean accept;
}
