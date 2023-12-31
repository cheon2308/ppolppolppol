package com.ppol.alarm.dto.common;

import com.ppol.alarm.util.constatnt.enums.DataType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AlarmReferenceDto {

	private int referenceId;
	private String data;
	private Long targetId;
	private DataType type;
}
