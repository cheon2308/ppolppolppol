package com.ppol.personal.util.converter;

import com.ppol.personal.util.constatnt.enums.CharacterColor;
import com.ppol.personal.util.constatnt.enums.FaceType;
import com.ppol.personal.util.converter.common.CommonEnumConverter;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class FaceTypeConverter extends CommonEnumConverter<FaceType> {

	public FaceTypeConverter() {
		super(FaceType.class);
	}
}