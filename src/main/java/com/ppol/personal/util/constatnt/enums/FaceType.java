package com.ppol.personal.util.constatnt.enums;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.ppol.personal.exception.exception.EnumConvertException;
import com.ppol.personal.util.constatnt.enums.global.BasicEnum;

public enum FaceType implements BasicEnum {

	ONE("1"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6");

	private final String code;

	FaceType(String code) {
		this.code = code;
	}

	@Override
	@JsonValue
	public String getCode() {
		return code;
	}

	@JsonCreator
	public static FaceType of(String code) {
		return Arrays.stream(FaceType.values())
			.filter(faceType -> faceType.getCode().equals(code))
			.findAny()
			.orElseThrow(() -> new EnumConvertException("FaceType", code));
	}
}
