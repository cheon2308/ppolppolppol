package com.ppol.auth.util.converter;



import com.ppol.auth.util.constatnt.enums.OpenStatus;
import com.ppol.auth.util.converter.common.CommonEnumConverter;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class OpenStatusConverter extends CommonEnumConverter<OpenStatus> {

	public OpenStatusConverter() {
		super(OpenStatus.class);
	}
}
