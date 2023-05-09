package com.ppol.personal.util.constatnt.enums;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.ppol.personal.exception.exception.EnumConvertException;
import com.ppol.personal.util.constatnt.enums.global.BasicEnum;

public enum CharacterColor implements BasicEnum {

	RED("1"),
	ORANGE("2"),
	YELLOW("3"),
	GREEN("4"),
	BLUE("5"),
	INDIGO("6"),
	VIOLET("7"),
	PINK("8");

	private final String code;

	CharacterColor(String code) {
		this.code = code;
	}

	@Override
	@JsonValue
	public String getCode() {
		return code;
	}

	@JsonCreator
	public static CharacterColor of(String code) {
		return Arrays.stream(CharacterColor.values())
			.filter(characterColor -> characterColor.getCode().equals(code))
			.findAny()
			.orElseThrow(() -> new EnumConvertException("CharacterColor", code));
	}
}
