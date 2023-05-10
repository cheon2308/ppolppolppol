package com.ppol.personal.util.constatnt.enums;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.ppol.personal.exception.exception.EnumConvertException;
import com.ppol.personal.util.constatnt.enums.global.BasicEnum;

public enum CharacterColor implements BasicEnum {

	RED("red", 1),
	ORANGE("orange", 2),
	YELLOW("yellow", 3),
	GREEN("green", 4),
	BLUE("blue", 5),
	INDIGO("indigo", 6),
	VIOLET("violet", 7),
	PINK("pink", 8);

	private final String code;
	private final int numCode;

	CharacterColor(String code, int numCode) {
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
	public static CharacterColor of(int numCode) {
		return Arrays.stream(CharacterColor.values())
			.filter(characterColor -> characterColor.getNumCode() == numCode)
			.findAny()
			.orElseThrow(() -> new EnumConvertException("AlbumColor", String.valueOf(numCode)));
	}
}
