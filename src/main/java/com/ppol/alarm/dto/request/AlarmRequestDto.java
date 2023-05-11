package com.ppol.alarm.dto.request;

import java.util.List;

import com.ppol.alarm.dto.common.AlarmReferenceDto;
import com.ppol.alarm.util.constatnt.enums.AlarmType;

import jakarta.validation.constraints.NotNull;
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
public class AlarmRequestDto {

	@NotNull
	private Long userId;

	@NotNull
	private AlarmType alarmType;

	private String alarmImage;

	private List<AlarmReferenceDto> alarmReferenceDtoList;
}
