package com.ppol.personal.util.constatnt.enums;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.ppol.personal.exception.exception.EnumConvertException;
import com.ppol.personal.util.constatnt.enums.global.BasicEnum;

public enum FaceType implements BasicEnum {

	ONE("one", 1),
	TWO("two", 2),
	THREE("three", 3),
	FOUR("four", 4),
	FIVE("five", 5),
	SIX("six", 6);

	private final String code;
	private final int numCode;

	FaceType(String code, int numCode) {
		this.code = code;
		this.numCode = numCode;
	}

	@Override
	public String getCode() {
		return code;
	}

	@JsonValue
	public int getNumCode() {
		return numCode;
	}

	@JsonCreator
	public static FaceType of(int numCode) {
		return Arrays.stream(FaceType.values())
			.filter(faceType -> faceType.getNumCode() == numCode)
			.findAny()
			.orElseThrow(() -> new EnumConvertException("AlbumColor", String.valueOf(numCode)));
	}
}
