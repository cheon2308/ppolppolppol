package com.ppol.personal.util.converter;

import com.ppol.personal.util.constatnt.enums.MeshType;
import com.ppol.personal.util.constatnt.enums.RoomMap;
import com.ppol.personal.util.converter.common.CommonEnumConverter;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoomMapConverter extends CommonEnumConverter<RoomMap> {

	public RoomMapConverter() {
		super(RoomMap.class);
	}
}