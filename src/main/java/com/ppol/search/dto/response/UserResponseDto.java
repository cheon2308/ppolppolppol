package com.ppol.search.dto.response;

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
	private String image;
	private String intro;
	private String phone;
	private boolean isFollow;
	private int followerCount;
	private int followingCount;

}
