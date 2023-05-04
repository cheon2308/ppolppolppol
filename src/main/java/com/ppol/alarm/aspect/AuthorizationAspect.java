package com.ppol.alarm.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ppol.alarm.util.feign.AuthFeign;

import lombok.RequiredArgsConstructor;

/**
 * 	사용자 권한 인증 및 사용자 ID를 저장하기 위한 AOP Class
 */
@Aspect
@RequiredArgsConstructor
@Component
public class AuthorizationAspect {

	private final AuthFeign authFeign;

	/**
	 * 	모든 컨트롤러의 메서드들 실행이전에 실행한다.
	 * 	헤더의 Authorization으로 부터 엑세스 토큰을 받아서 인증 서버로 부터 userId를 받고 REQUEST에 저장한다.
	 */
	@Before("execution(* com.ppol.*.controller.*.*(..))"
		+ "&& !execution(* com.ppol.alarm.controller.AlarmController.alarmCreate(..))")
	public void getUserIdFromHeader() {
		String accessToken = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest()
			.getHeader("Authorization");

		Long userId = getUserIdFromAuthorization(accessToken);

		RequestContextHolder.currentRequestAttributes().setAttribute("userId", userId, RequestAttributes.SCOPE_REQUEST);
	}

	private Long getUserIdFromAuthorization(String accessToken) {
		return authFeign.accessToken(accessToken);
	}
}