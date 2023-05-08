package com.ppol.alarm.util.converter.common;

import com.ppol.alarm.util.constatnt.enums.global.BasicEnum;
import com.ppol.alarm.util.constatnt.enums.global.CommonEnumValueConverter;

import jakarta.persistence.AttributeConverter;

/**
 * 자바의 Enum들을 변환해서 DB에 저장하고 DB에서 불러오는 converter들의 basic converter
 */
public class CommonEnumConverter<E extends Enum<E> & BasicEnum> implements AttributeConverter<E, String> {

	// 어떤 Enum에 대해 Converter를 만들지 각 Converter에서 생성자를 통해 넣어준다.
	private final Class<E> enumClass;

	public CommonEnumConverter(Class<E> enumClass) {
		this.enumClass = enumClass;
	}

	/**
	 * 해당 내용들은 AttributeConverter 인터페이스의 메서드들로써 해당 내용들을 구현해야 한다.
	 * convertToDatabaseColumn은 자바 enum을 변환해서 DB에 어떤 형식으로 저장할지를 정의하는 메서드
	 * convertToEntityAttribute은 DB 데이터를 어떤 방식으로 자바 enum으로 변환하는지를 정의하는 메서드
	 * 각 내용은 {@link CommonEnumValueConverter}에서 공통으로 사용할 수 있도록 정의 했다.
	 */
	@Override
	public String convertToDatabaseColumn(E attribute) {
		return CommonEnumValueConverter.toCode(attribute);
	}

	@Override
	public E convertToEntityAttribute(String dbData) {
		return CommonEnumValueConverter.ofCode(enumClass, dbData);
	}
}
