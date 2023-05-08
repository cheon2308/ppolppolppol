package com.ppol.alarm.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("com.ppol.*.util.feign")
public class FeignClientConfig {

}