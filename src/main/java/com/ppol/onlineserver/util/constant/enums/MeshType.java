package com.ppol.onlineserver.util.constant.enums;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.ppol.onlineserver.exception.exception.EnumConvertException;
import com.ppol.onlineserver.util.constant.enums.global.BasicEnum;

public enum MeshType implements BasicEnum {

	BASIC("basic", 1), RABBIT("rabbit", 2), PIG("pig", 3);

	private final String code;
	private final int numCode;

	MeshType(String code, int numCode) {
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
	public static MeshType of(int numCode) {
		return Arrays.stream(MeshType.values())
			.filter(meshType -> meshType.getNumCode() == numCode)
			.findAny()
			.orElseThrow(() -> new EnumConvertException("AlbumColor", String.valueOf(numCode)));
	}
}
