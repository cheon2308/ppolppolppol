package com.ppol.user.util.converter;

import com.ppol.user.util.constatnt.enums.OpenStatus;
import com.ppol.user.util.converter.common.CommonEnumConverter;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class OpenStatusConverter extends CommonEnumConverter<OpenStatus> {

	public OpenStatusConverter() {
		super(OpenStatus.class);
	}
}
