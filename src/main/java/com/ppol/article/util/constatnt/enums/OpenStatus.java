package com.ppol.article.util.constatnt.enums;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.ppol.article.exception.exception.EnumConvertException;
import com.ppol.article.util.constatnt.enums.global.BasicEnum;

/*
	특정 컨텐츠에 대한 공개 여부
	Public : 공개
	Private : 비공개
 */
public enum OpenStatus implements BasicEnum {

	PUBLIC("public"), PRIVATE("private");

	private final String code;

	OpenStatus(String code) {
		this.code = code;
	}

	@Override
	@JsonValue
	public String getCode() {
		return this.code;
	}

	@JsonCreator
	public static OpenStatus from(String code) {

		return Arrays.stream(OpenStatus.values())
			.filter(o -> o.code.equals(code))
			.findAny()
			.orElseThrow(() -> new EnumConvertException("OpenStatus", code));
	}
}
