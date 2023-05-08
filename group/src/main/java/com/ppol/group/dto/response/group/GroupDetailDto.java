package com.ppol.group.dto.response.group;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import com.ppol.group.dto.response.UserResponseDto;
import com.ppol.group.entity.group.Group;
import com.ppol.group.util.DateTimeUtils;

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
public class GroupDetailDto {

	private Long groupId;

	private String title;

	private Set<UserResponseDto> userList;

	private UserResponseDto owner;

	private LocalDateTime createdAt;

	private String createString;

	public static GroupDetailDto of(Group group) {
		return GroupDetailDto.builder()
			.groupId(group.getId())
			.title(group.getTitle())
			.userList(group.getUserList().stream().map(UserResponseDto::of).collect(Collectors.toSet()))
			.owner(UserResponseDto.of(group.getOwner()))
			.createdAt(group.getCreatedAt())
			.createString(DateTimeUtils.getString(group.getCreatedAt()))
			.build();
	}
}
