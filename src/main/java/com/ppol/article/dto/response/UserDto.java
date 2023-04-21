package com.ppol.article.dto.response;

import com.ppol.article.entity.user.User;

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
public class UserDto {

	private Long userId;

	private String username;

	private String profileImage;

	public static UserDto of(User user) {
		return UserDto.builder()
			.userId(user.getId())
			.username(user.getUsername())
			.profileImage(user.getImage())
			.build();
	}
}
