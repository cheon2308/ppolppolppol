package com.ppol.personal.util.converter;

import com.ppol.personal.util.constatnt.enums.FaceType;
import com.ppol.personal.util.constatnt.enums.MeshType;
import com.ppol.personal.util.converter.common.CommonEnumConverter;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class MeshTypeConverter extends CommonEnumConverter<MeshType> {

	public MeshTypeConverter() {
		super(MeshType.class);
	}
}