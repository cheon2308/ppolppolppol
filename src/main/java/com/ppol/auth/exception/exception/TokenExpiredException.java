package com.ppol.auth.exception.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TokenExpiredException extends RuntimeException {

	private final String msg;
}
