package com.ppol.group.util.converter;

import com.ppol.group.util.constatnt.enums.OpenStatus;
import com.ppol.group.util.converter.common.CommonEnumConverter;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class OpenStatusConverter extends CommonEnumConverter<OpenStatus> {

	public OpenStatusConverter() {
		super(OpenStatus.class);
	}
}
