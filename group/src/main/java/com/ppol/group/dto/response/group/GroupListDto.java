package com.ppol.group.dto.response.group;

import com.ppol.group.dto.response.UserResponseDto;
import com.ppol.group.entity.group.Group;

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
public class GroupListDto {

	private Long groupId;

	private String title;

	private UserResponseDto owner;

	private int userCount;

	public static GroupListDto of(Group group) {

		return GroupListDto.builder()
			.groupId(group.getId())
			.title(group.getTitle())
			.owner(UserResponseDto.of(group.getOwner()))
			.userCount(group.getUserList().size())
			.build();
	}
}
