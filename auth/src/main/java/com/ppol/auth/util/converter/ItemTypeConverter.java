package com.ppol.auth.util.converter;

import com.ppol.auth.util.constatnt.enums.ItemType;
import com.ppol.auth.util.converter.common.CommonEnumConverter;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ItemTypeConverter extends CommonEnumConverter<ItemType> {

	public ItemTypeConverter() {
		super(ItemType.class);
	}
}

