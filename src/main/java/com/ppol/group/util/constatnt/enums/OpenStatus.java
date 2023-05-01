package com.ppol.group.util.constatnt.enums;

import com.ppol.group.util.constatnt.enums.global.BasicEnum;

/**
 * 	특정 컨텐츠에 대한 공개 여부
 * 	PUBLIC : 공개
 * 	PRIVATE : 비공개
 */
public enum OpenStatus implements BasicEnum {

	PUBLIC("public"), PRIVATE("private");

	private final String code;

	OpenStatus(String code) {
		this.code = code;
	}

	@Override
	public String getCode() {
		return this.code;
	}
}
