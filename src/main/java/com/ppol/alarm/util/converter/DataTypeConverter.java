package com.ppol.alarm.util.converter;

import com.ppol.alarm.util.constatnt.enums.DataType;
import com.ppol.alarm.util.converter.common.CommonEnumConverter;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DataTypeConverter extends CommonEnumConverter<DataType> {

	public DataTypeConverter() {
		super(DataType.class);
	}
}
