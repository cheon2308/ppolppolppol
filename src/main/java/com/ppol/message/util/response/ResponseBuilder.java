package com.ppol.message.util.response;

import org.springframework.http.ResponseEntity;

public class ResponseBuilder {

	private static ResponseEntity<?> of(Response<?> response) {
		return ResponseEntity.status(response.getStatus()).body(response);
	}

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
