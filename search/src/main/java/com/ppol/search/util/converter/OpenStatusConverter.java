package com.ppol.search.util.converter;

import com.ppol.search.util.constatnt.enums.OpenStatus;
import com.ppol.search.util.converter.common.CommonEnumConverter;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class OpenStatusConverter extends CommonEnumConverter<OpenStatus> {

	public OpenStatusConverter() {
		super(OpenStatus.class);
	}
}
