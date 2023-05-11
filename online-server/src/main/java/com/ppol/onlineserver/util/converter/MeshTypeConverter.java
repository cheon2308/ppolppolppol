package com.ppol.onlineserver.util.converter;

import com.ppol.onlineserver.util.constant.enums.MeshType;
import com.ppol.onlineserver.util.converter.common.CommonEnumConverter;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class MeshTypeConverter extends CommonEnumConverter<MeshType> {

	public MeshTypeConverter() {
		super(MeshType.class);
	}
}