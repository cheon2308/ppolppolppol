package com.ppol.article.util.response;

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

/**
 *	백엔드 API 서버에서 일정한 반환 형식을 클라이언트로 내보내주기 위해 사용할 Class
 *	기본적으로 데이터, 상태정보, 메시지, 타임스탬프, 추가정보(메타데이터)를 담는다.
 *	이 때 정상응답의 경우에는 data에 값이 담기고 message에는 크게 의미를 두지 않는 값이 담긴다.
 *	예외응답의 경우에는 data에는 null 값이 담기고 message에 예외 메시지를 담아서 보내준다.
 *	metadata에는 data가 Slice, Page의 경우 다양한 메타 데이터들이 담긴다.
 */
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

	/**
	 *	기본 생성자, timestamp는 now
	 */
	private Response(D data, HttpStatus status, String message, Map<String, String> metadata) {
		this.data = data;
		this.status = status;
		this.message = message;
		this.metadata = metadata;
		this.timestamp = LocalDateTime.now();
	}

	/**
	 *	Slice, Page 형식의 데이터에 대한 metadata를 생성하는 메서드
	 */
	private static Map<String, String> metadata(Slice<?> slice) {

		Map<String, String> metadata = new HashMap<>();

		// 공통적으로 들어가는 4종류의 데이터
		metadata.put("size", Integer.toString(slice.getSize()));
		metadata.put("count", Integer.toString(slice.getNumberOfElements()));
		metadata.put("hasNext", Boolean.toString(slice.hasNext()));
		metadata.put("pageNumber", Integer.toString(slice.getNumber()));

		// Page의 경우에 2가지 데이터를 추가
		if (slice instanceof Page<?> page) {
			metadata.put("totalPages", Integer.toString(page.getTotalPages()));
			metadata.put("totalCount", Long.toString(page.getTotalElements()));
		}

		return metadata;
	}

	/**
	 *	data의 형식이 Page 혹은 Slice인 경우 메타데이터를 포함한 Response객체를 생성
	 *	이 외에는 메타데이터가 null인 Response객체 생성
	 */
	public static <T> Response<?> of(T data, HttpStatus status, String message) {

		if (data instanceof Page<?> page) {

			return new Response<>(page.getContent(), status, message, metadata(page));

		} else if (data instanceof Slice<?> slice) {

			return new Response<>(slice.getContent(), status, message, metadata(slice));

		} else {

			return new Response<>(data, status, message, null);

		}
	}

	/**
	 *	아래에는 {@link ResponseBuilder}에서 사용할 우리 서버에서 사용하는 응답 status에 대해 각각 status를 포함한 Response 객체를 만드는 메서드들
	 */
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