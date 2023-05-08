package com.ppol.article.exception.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 	S3 파일 서버관련 에러
 * 	Exception 예시
 * 	1. 파일 업로드 시 자체 에러
 * 	2. 파일 삭제 시 자체 에러
 */
@Getter
@RequiredArgsConstructor
public class S3Exception extends RuntimeException{

	private final String msg;
}
