package com.ppol.user.util.constatnt.enums;



import com.ppol.user.util.constatnt.enums.global.BasicEnum;

import lombok.Getter;

/**
 * 	사용자 로그인 종류
 * 	GOOGLE : 구글 로그인
 * 	NAVER : 네이버 로그인
 * 	KAKAO : 카카오 로그인
 * 	EMAIL : 이메일 로그인
 */
@Getter
public enum Provider implements BasicEnum {

	GOOGLE("G"), NAVER("N"), KAKAO("K"), EMAIL("E");

	private final String code;

	Provider(String code) {
		this.code = code;
	}

	@Override
	public String getCode() {
		return this.code;
	}
}


