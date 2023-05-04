package com.ppol.user.util.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import com.ppol.user.config.FeignClientConfig;
import com.ppol.user.dto.feign.AlarmRequestDto;

@FeignClient(name = "AlarmFeign", url = "${feign.url.base}"
	+ "${feign.url.alarm.service}" + "${feign.url.alarm.controller}", configuration = FeignClientConfig.class)
public interface AlarmFeign {

	@PostMapping
	ResponseEntity<?> alarmCreate(AlarmRequestDto alarmRequestDto);
}