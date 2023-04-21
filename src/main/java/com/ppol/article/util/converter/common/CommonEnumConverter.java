package com.ppol.article.util.converter.common;

import com.ppol.article.util.constatnt.enums.global.BasicEnum;
import com.ppol.article.util.constatnt.enums.global.CommonEnumValueConverter;

import jakarta.persistence.AttributeConverter;

public class CommonEnumConverter<E extends Enum<E> & BasicEnum> implements AttributeConverter<E, String> {

	private final Class<E> enumClass;

	public CommonEnumConverter(Class<E> enumClass) {
		this.enumClass = enumClass;
	}

	@Override
	public String convertToDatabaseColumn(E attribute) {
		return CommonEnumValueConverter.toCode(attribute);
	}

	@Override
	public E convertToEntityAttribute(String dbData) {
		return CommonEnumValueConverter.ofCode(enumClass, dbData);
	}
}
