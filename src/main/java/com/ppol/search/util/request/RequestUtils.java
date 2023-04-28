package com.ppol.search.util.request;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * 	클라이언트로 받은 Request들을 처리 할 때 사용할 수 있는 메서드들을 모아 놓은 util class
 */
public class RequestUtils {

	/**
	 * AOP에서 토큰을 처리해서 Request에 담아 놓은 userId를 꺼내는 메서드
	 */
	public static Long getUserId() {

		String userId = (String)RequestContextHolder.currentRequestAttributes()
			.getAttribute("userId", RequestAttributes.SCOPE_REQUEST);

		if(userId == null) {
			throw new RuntimeException();
		}

		return Long.parseLong(userId);
	}
}