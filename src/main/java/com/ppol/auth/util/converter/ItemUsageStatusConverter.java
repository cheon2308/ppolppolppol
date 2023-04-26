package com.ppol.auth.util.converter;



import com.ppol.auth.util.constatnt.enums.ItemUsageStatus;
import com.ppol.auth.util.converter.common.CommonEnumConverter;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ItemUsageStatusConverter extends CommonEnumConverter<ItemUsageStatus> {

	public ItemUsageStatusConverter() {
		super(ItemUsageStatus.class);
	}
}

