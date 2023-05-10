package com.ppol.personal.util.constatnt.enums;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.ppol.personal.exception.exception.EnumConvertException;
import com.ppol.personal.exception.exception.InvalidAlarmCodeException;
import com.ppol.personal.util.constatnt.enums.global.BasicEnum;

public enum AlbumColor implements BasicEnum {

	RED("1"), BLUE("2"), GREEN("3");

	private final String code;

	AlbumColor(String code) {
		this.code = code;
	}

	@Override
	@JsonValue
	public String getCode() {
		return code;
	}

	@JsonCreator
	public static AlbumColor of(String code) {
		return Arrays.stream(AlbumColor.values())
			.filter(albumColor -> albumColor.getCode().equals(code))
			.findAny()
			.orElseThrow(() -> new EnumConvertException("AlbumColor", code));
	}
}
