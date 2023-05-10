package com.ppol.personal.util.constatnt.enums;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.ppol.personal.exception.exception.EnumConvertException;
import com.ppol.personal.util.constatnt.enums.global.BasicEnum;

public enum MeshType implements BasicEnum {

	BASIC("1"), RABBIT("2"), PIG("3");

	private final String code;

	MeshType(String code) {
		this.code = code;
	}

	@Override
	@JsonValue
	public String getCode() {
		return code;
	}

	@JsonCreator
	public static MeshType of(String code) {
		return Arrays.stream(MeshType.values())
			.filter(meshType -> meshType.getCode().equals(code))
			.findAny()
			.orElseThrow(() -> new EnumConvertException("MeshType", code));
	}
}
