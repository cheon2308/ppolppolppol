package com.ppol.onlineserver.util.converter;

import com.ppol.onlineserver.util.constant.enums.CharacterColor;
import com.ppol.onlineserver.util.converter.common.CommonEnumConverter;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CharacterColorConverter extends CommonEnumConverter<CharacterColor> {

	public CharacterColorConverter() {
		super(CharacterColor.class);
	}
}