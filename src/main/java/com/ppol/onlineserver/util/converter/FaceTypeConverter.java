package com.ppol.onlineserver.util.converter;

import com.ppol.onlineserver.util.constant.enums.FaceType;
import com.ppol.onlineserver.util.converter.common.CommonEnumConverter;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class FaceTypeConverter extends CommonEnumConverter<FaceType> {

	public FaceTypeConverter() {
		super(FaceType.class);
	}
}