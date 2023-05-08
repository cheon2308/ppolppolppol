package com.ppol.article.util.converter;

import com.ppol.article.util.constatnt.enums.OpenStatus;
import com.ppol.article.util.converter.common.CommonEnumConverter;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class OpenStatusConverter extends CommonEnumConverter<OpenStatus> {

	public OpenStatusConverter() {
		super(OpenStatus.class);
	}
}
