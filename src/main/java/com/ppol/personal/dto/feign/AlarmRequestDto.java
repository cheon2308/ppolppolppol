package com.ppol.personal.dto.feign;

import java.util.List;

import com.ppol.personal.util.constatnt.enums.AlarmType;

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

	private List<AlarmReferenceDto> alarmReferenceDtoList;
}