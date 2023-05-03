package com.ppol.group.util.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import com.ppol.group.config.FeignClientConfig;
import com.ppol.group.dto.request.alarm.AlarmRequestDto;

@FeignClient(name = "AlarmFeign", url = "${feign.url.base}" + "${feign.url.alarm.service}", configuration = FeignClientConfig.class)
public interface AlarmFeign {

	@PostMapping
	ResponseEntity<?> alarmCreate(AlarmRequestDto alarmRequestDto);
}
