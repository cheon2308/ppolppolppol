package com.ppol.personal.util.converter;

import com.ppol.personal.util.constatnt.enums.AlbumColor;
import com.ppol.personal.util.converter.common.CommonEnumConverter;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AlbumColorConverter extends CommonEnumConverter<AlbumColor> {

	public AlbumColorConverter() {
		super(AlbumColor.class);
	}
}