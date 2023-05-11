package com.ppol.personal.util.constatnt.enums;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.ppol.personal.exception.exception.EnumConvertException;
import com.ppol.personal.util.constatnt.enums.global.BasicEnum;

public enum CharacterColor implements BasicEnum {

	WHITE("white", 1),
	RED("red", 2),
	ORANGE("orange", 3),
	YELLOW("yellow", 4),
	GREEN("green", 5),
	BLUE("blue", 6),
	INDIGO("indigo", 7),
	VIOLET("violet", 8),
	PINK("pink", 9),
	TURQUOISE("turquoise", 10),
	GRAY("gray", 11);

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
