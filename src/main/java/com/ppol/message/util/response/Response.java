package com.ppol.message.util.response;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Response<D> {

	private D data;
	private HttpStatus status;
	private String message;
	private LocalDateTime timestamp;
	private Map<String, String> metadata;

	private Response(D data, HttpStatus status, String message, Map<String, String> metadata) {
		this.data = data;
		this.status = status;
		this.message = message;
		this.metadata = metadata;
		this.timestamp = LocalDateTime.now();
	}

	private static <T> Response<T> of(T data, HttpStatus status, String message, Map<String, String> metadata) {

		return new Response<>(data, status, message, metadata);
	}

	private static Map<String, String> metadata(Slice<?> slice) {

		Map<String, String> metadata = new HashMap<>();

		metadata.put("size", Integer.toString(slice.getSize()));
		metadata.put("count", Integer.toString(slice.getNumberOfElements()));
		metadata.put("hasNext", Boolean.toString(slice.hasNext()));
		metadata.put("pageNumber", Integer.toString(slice.getNumber()));

		if (slice instanceof Page<?> page) {

			metadata.put("totalPages", Integer.toString(page.getTotalPages()));
			metadata.put("totalCount", Long.toString(page.getTotalElements()));
		}

		return metadata;
	}

	public static <T> Response<?> of(T data, HttpStatus status, String message) {

		if (data instanceof Page<?> page) {

			return of(page.getContent(), status, message, metadata(page));

		} else if (data instanceof Slice<?> slice) {

			return of(slice.getContent(), status, message, metadata(slice));

		} else {

			return Response.of(data, status, message, null);

		}
	}

	public static <T> Response<?> ok(T data) {
		return of(data, HttpStatus.OK, "Ok");
	}

	public static <T> Response<?> created(T data) {
		return of(data, HttpStatus.CREATED, "Created");
	}

	public static Response<?> error(HttpStatus status, String msg) {
		return of(null, status, msg);
	}

	public static Response<?> badRequest(String msg) {
		return error(HttpStatus.BAD_REQUEST, msg);
	}

	public static Response<?> unauthorized(String msg) {
		return error(HttpStatus.UNAUTHORIZED, msg);
	}

	public static Response<?> forbidden(String msg) {
		return error(HttpStatus.FORBIDDEN, msg);
	}

	public static Response<?> internalServerError(String msg) {
		return error(HttpStatus.INTERNAL_SERVER_ERROR, msg);
	}
}