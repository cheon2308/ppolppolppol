package com.ppol.message.util.feign;

import org.springframework.stereotype.Component;

import com.ppol.message.exception.exception.TokenExpiredException;

import feign.Response;
import feign.codec.ErrorDecoder;

@Component
public class FeignErrorDecoder implements ErrorDecoder {
	@Override
	public Exception decode(String methodKey, Response response) {

		if (response.status() == 401) {
			return new TokenExpiredException();
		}

		return new RuntimeException();
	}
}
