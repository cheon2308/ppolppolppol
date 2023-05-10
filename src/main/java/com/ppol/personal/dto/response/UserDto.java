package com.ppol.personal.dto.response;

import com.ppol.personal.entity.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 사용자 정보를 담아서 반환하기 위한 DTO
 */
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

	// 팔로워 수
	private int followerCount;

	// 팔로잉 수
	private int followingCount;

	public static UserDto of(User user) {
		return UserDto.builder()
			.userId(user.getId())
			.username(user.getUsername())
			.profileImage(user.getImage())
			.followerCount(user.getFollowerCount())
			.followingCount(user.getFollowingCount())
			.build();
	}
}
