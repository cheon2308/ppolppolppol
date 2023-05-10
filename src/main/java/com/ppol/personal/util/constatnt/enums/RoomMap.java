package com.ppol.personal.util.constatnt.enums;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.ppol.personal.exception.exception.EnumConvertException;
import com.ppol.personal.util.constatnt.enums.global.BasicEnum;

public enum RoomMap implements BasicEnum {
	MIDDLE_AGE("1"), WATER("2"), CITY("3");

	private final String code;

	RoomMap(String code) {
		this.code = code;
	}

	@Override
	@JsonValue
	public String getCode() {
		return code;
	}

	@JsonCreator
	public static RoomMap of(String code) {
		return Arrays.stream(RoomMap.values())
			.filter(roomMap -> roomMap.getCode().equals(code))
			.findAny()
			.orElseThrow(() -> new EnumConvertException("RoomMap", code));
	}
}
