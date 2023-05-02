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
public class UserResponseDto {

	private Long userId;

	private String username;

	private String profileImage;

	public static UserResponseDto of(User user) {
		return UserResponseDto.builder()
			.userId(user.getId())
			.username(user.getUsername())
			.profileImage(user.getImage())
			.build();
	}
}
