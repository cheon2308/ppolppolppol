package com.ppol.search.util.constatnt.enums.global;

import java.util.EnumSet;

import com.ppol.search.exception.exception.EnumConvertException;

/**
 * {@link BasicEnum} enum과 code를 상호변환하기 위한 공통 클래스
 */
public class CommonEnumValueConverter {

	/**
	 *	target enum의 value들을 돌면서 code에 해당하는 enum을 찾아서 반환한다.
	 *	이때	비어있는 code를 허용하지 않는다.
	 */
	public static <T extends Enum<T> & BasicEnum> T ofCode(Class<T> enumClass, String code) {

		if(code.isBlank()) {
			return null;
		}

		return EnumSet.allOf(enumClass).stream()
			.filter(v -> v.getCode().equals(code))
			.findAny()
			.orElseThrow(() -> new EnumConvertException(enumClass.getName(), code));
	}

	/**
	 *	각 enum의 getCode 메서드를 활용한다.
	 *	이때 null을 허용하지 않는다.
	 */
	public static <T extends Enum<T> & BasicEnum> String toCode(T enumValue) {

		if(enumValue == null) {
			throw new EnumConvertException();
		}

		return enumValue.getCode();
	}
}