package com.ppol.article.exception.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EnumConvertException extends RuntimeException {

	private String enumName;
	private String code;
}
