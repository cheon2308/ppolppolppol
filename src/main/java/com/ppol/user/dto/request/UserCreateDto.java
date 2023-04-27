package com.ppol.user.dto.request;

import com.ppol.user.util.constatnt.enums.Provider;

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
public class UserCreateDto {

	private String accountId;
	private String password;
	private String username;
	private Provider provider;

}
