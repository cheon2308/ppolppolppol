package com.ppol.group.util.converter;

import com.ppol.group.util.constatnt.enums.ItemType;
import com.ppol.group.util.converter.common.CommonEnumConverter;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ItemTypeConverter extends CommonEnumConverter<ItemType> {

	public ItemTypeConverter() {
		super(ItemType.class);
	}
}

