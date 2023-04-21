package com.ppol.article.util.constatnt.enums.global;

import java.util.EnumSet;

import com.ppol.article.exception.exception.EnumConvertException;

/**
 * {@link BasicEnum} enum과 code를 상호변환하기 위한 공통 클래스
 */
public class CommonEnumValueConverter {

	public static <T extends Enum<T> & BasicEnum> T ofCode(Class<T> enumClass, String code) {

		if(code.isBlank()) {
			return null;
		}

		return EnumSet.allOf(enumClass).stream()
			.filter(v -> v.getCode().equals(code))
			.findAny()
			.orElseThrow(() -> new EnumConvertException(enumClass.getName(), code));
	}

	public static <T extends Enum<T> & BasicEnum> String toCode(T enumValue) {

		if(enumValue == null) {
			throw new EnumConvertException();
		}

		return enumValue.getCode();
	}
}
