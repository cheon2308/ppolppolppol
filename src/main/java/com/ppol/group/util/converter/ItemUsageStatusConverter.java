package com.ppol.group.util.converter;

import com.ppol.group.util.constatnt.enums.ItemUsageStatus;
import com.ppol.group.util.converter.common.CommonEnumConverter;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ItemUsageStatusConverter extends CommonEnumConverter<ItemUsageStatus> {

	public ItemUsageStatusConverter() {
		super(ItemUsageStatus.class);
	}
}

