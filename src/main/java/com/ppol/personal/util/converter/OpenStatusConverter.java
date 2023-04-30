package com.ppol.personal.util.converter;



import com.ppol.personal.util.constatnt.enums.OpenStatus;
import com.ppol.personal.util.converter.common.CommonEnumConverter;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class OpenStatusConverter extends CommonEnumConverter<OpenStatus> {

	public OpenStatusConverter() {
		super(OpenStatus.class);
	}
}
