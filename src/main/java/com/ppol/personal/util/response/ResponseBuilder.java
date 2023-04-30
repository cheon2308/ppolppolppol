package com.ppol.personal.util.response;

import org.springframework.http.ResponseEntity;

/**
 * 	서버의 일관된 형식의 Response를 생성해주는 Builder Class
 * 	해당 클래스를 통해 status에 맞는 Response를 생성하도록 한다.
 */
public class ResponseBuilder {

	/**
	 *	각 메서드들에서 공통적으로 접근해서 Response객체를 감싸는 ResponseEntity를 생성하는 메서드
	 */
	private static ResponseEntity<?> of(Response<?> response) {
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	/**
	 *	아래 메서드들을 각각 status에 맞게 데이터 혹은 메시지를 담아서 사용하도록 한다.
	 */
	public static <T> ResponseEntity<?> ok(T data) {
		return of(Response.ok(data));
	}

	public static <T> ResponseEntity<?> created(T data) {
		return of(Response.created(data));
	}

	public static <T> ResponseEntity<?> badRequest(String msg) {
		return of(Response.badRequest(msg));
	}

	public static <T> ResponseEntity<?> unauthorized(String msg) {
		return of(Response.unauthorized(msg));
	}

	public static <T> ResponseEntity<?> forbidden(String msg) {
		return of(Response.forbidden(msg));
	}

	public static <T> ResponseEntity<?> internalServerError(String msg) {
		return of(Response.internalServerError(msg));
	}
}
