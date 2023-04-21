package com.ppol.message.util.request;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class RequestUtils {
	public static String getUserId() {
		return (String) RequestContextHolder.currentRequestAttributes()
			.getAttribute("userId", RequestAttributes.SCOPE_REQUEST);
	}
}