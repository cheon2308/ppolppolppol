package com.ppol.alarm.util.converter;

import com.ppol.alarm.util.constatnt.enums.AlarmType;
import com.ppol.alarm.util.converter.common.CommonEnumConverter;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AlarmTypeConverter extends CommonEnumConverter<AlarmType> {
	public AlarmTypeConverter() {
		super(AlarmType.class);
	}
}
