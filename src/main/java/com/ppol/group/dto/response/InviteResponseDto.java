package com.ppol.group.dto.response;

import com.ppol.group.entity.group.GroupInvite;

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
public class InviteResponseDto {

	private Long inviteId;
	private Long groupId;
	private UserResponseDto user;

	public static InviteResponseDto of(GroupInvite invite) {
		return InviteResponseDto.builder()
			.inviteId(invite.getId())
			.groupId(invite.getGroup().getId())
			.user(UserResponseDto.of(invite.getUser()))
			.build();
	}
}
