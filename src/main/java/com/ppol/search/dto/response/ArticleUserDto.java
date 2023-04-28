package com.ppol.search.dto.response;

import com.ppol.search.entity.user.User;

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
public class ArticleUserDto {

	private Long userId;

	private String username;

	private String profileImage;

	public static ArticleUserDto of(User user) {
		return ArticleUserDto.builder()
			.userId(user.getId())
			.username(user.getUsername())
			.profileImage(user.getImage())
			.build();
	}
}
