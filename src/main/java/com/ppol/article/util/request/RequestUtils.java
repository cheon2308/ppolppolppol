package com.ppol.article.util.request;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class RequestUtils {
	public static Long getUserId() {

		String userId = (String) RequestContextHolder.currentRequestAttributes()
			.getAttribute("userId", RequestAttributes.SCOPE_REQUEST);

		return userId == null ? null : Long.parseLong(userId);
	}
}