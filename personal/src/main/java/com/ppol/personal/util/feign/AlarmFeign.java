package com.ppol.personal.util.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import com.ppol.personal.config.FeignClientConfig;
import com.ppol.personal.dto.feign.AlarmRequestDto;

@FeignClient(name = "AlarmFeign", url = "${feign.url.base}"
	+ "${feign.url.alarm.service}" + "${feign.url.alarm.controller}", configuration = FeignClientConfig.class)
public interface AlarmFeign {

	@PostMapping
	ResponseEntity<?> alarmCreate(AlarmRequestDto alarmRequestDto);
}