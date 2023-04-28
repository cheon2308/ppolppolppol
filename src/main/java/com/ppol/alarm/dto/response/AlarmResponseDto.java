package com.ppol.alarm.dto.response;

import java.util.List;

import com.ppol.alarm.dto.common.AlarmReferenceDto;

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
public class AlarmResponseDto {

	private long alarmId;
	private String content;
	private long userId;
	private int state;
	private List<AlarmReferenceDto> alarmReferenceDtoList;
}
