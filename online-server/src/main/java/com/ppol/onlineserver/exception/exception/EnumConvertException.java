package com.ppol.onlineserver.exception.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 	ENUM -> <- DB 변환 시 에러를 처리하는 class
 * 	Exception 예시
 * 	1. 특정 enum에 대해서 code가 존재 하지 않는 경우
 * 	2. enum 변환 시 null이 오는 경우 등
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EnumConvertException extends RuntimeException {

	private String enumName;
	private String code;
}
