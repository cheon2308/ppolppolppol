package com.ppol.auth.dto.request;

import com.ppol.auth.util.constatnt.enums.Provider;

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
public class AuthDto {

	private String accountId;
	private String password;
	private Provider provider;
}
