package com.ppol.article.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class AuthorizationAspect {

	@Before("execution(* com.ppol.*.controller.*.*(..))")
	public void getUserIdFromHeader() {
		String accessToken = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes())
			.getRequest().getHeader("Authorization");

		String userId = getUserIdFromAuthorization(accessToken);

		RequestContextHolder.currentRequestAttributes().setAttribute("userId", userId, RequestAttributes.SCOPE_REQUEST);
	}

	// TODO 토큰을 가지고 userId를 가져오는 로직 필요
	private String getUserIdFromAuthorization(String accessToken) {
		return "1";
	}
}