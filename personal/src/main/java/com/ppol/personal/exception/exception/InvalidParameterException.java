package com.ppol.personal.exception.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class InvalidParameterException extends RuntimeException{

	private final String msg;
}
