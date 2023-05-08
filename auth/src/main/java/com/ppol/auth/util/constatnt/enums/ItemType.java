package com.ppol.auth.util.constatnt.enums;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.ppol.auth.exception.exception.EnumConvertException;
import com.ppol.auth.util.constatnt.enums.global.BasicEnum;

import jakarta.validation.constraints.NotNull;

/**
 * 3D 아이템 종류
 * 계속해서 추가 예정
 */
public enum ItemType implements BasicEnum {

	CHARACTER("캐릭터", "100"),
	ALBUM("앨범", "200"),
	BACKGROUND("배경", "300");

	private final String typeName;
	private final String code;

	ItemType(String typeName, String code) {
		this.typeName = typeName;
		this.code = code;
	}

	@Override
	public String getCode() {
		return code;
	}

	@JsonValue
	public String getTypeName() {
		return typeName;
	}

	@JsonCreator
	public static ItemType of(@NotNull String typeName) {
		return Arrays.stream(ItemType.values())
			.filter(v -> v.getTypeName().equals(typeName))
			.findAny()
			.orElseThrow(() -> new EnumConvertException(ItemType.class.getName(), typeName));
	}
}
