package com.ppol.article.util.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.ppol.article.config.FeignClientConfig;

@FeignClient(name = "AuthFeign", url = "${feign.url.base}"
	+ "${feign.url.auth.service}" + "${feign.url.auth.controller}", configuration = FeignClientConfig.class)
public interface AuthFeign {

	@GetMapping("/token")
	Long accessToken(String accessToken);
}
