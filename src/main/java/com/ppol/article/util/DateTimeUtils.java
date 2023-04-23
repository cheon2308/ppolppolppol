package com.ppol.article.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.ppol.article.util.constatnt.classes.DateTimeFormatString;

/**
 * 	서비스에서 일관적인 시간 형식을 클라이언트로 보내주기 위한 Util Class
 */
public class DateTimeUtils {

	/**
	 *	기본적으로 format을 지정하지 않고 Default로 BASIC 형식을 사용할 수 있는 메서드
	 */
	public static String getString(LocalDateTime timestamp) {
		return getString(timestamp, DateTimeFormatString.BASIC);
	}

	/**
	 *	timestamp를 통해 원하는 format형태로 나타냄
	 *	단, 이때 서비스에서 일정하게 어떤 시간 이내에는 절대 시간이 아닌 상대적 시간을 string 형태로 반환하도록 한다.
	 */
	public static String getString(LocalDateTime timestamp, String format) {

		// 현재 시간과 timestamp의 시간을 비교해서 day, hour, minutes 차이를 각각 구한다.
		LocalDateTime now = LocalDateTime.now();
		Duration duration = Duration.between(timestamp, now);

		long days = duration.toDays();
		long hours = duration.toHours();
		long minutes = duration.toMinutes();

		// 각 차이를 비교하면서 서비스에 맞는 일정한 문자열을 반환한다.
		if (days >= 5) {
			return timestamp.format(DateTimeFormatter.ofPattern(format));
		} else if (days > 0) {
			return days + "일 전";
		} else if (hours > 0) {
			return hours + "시간 전";
		} else if (minutes > 0) {
			return minutes + "분 전";
		} else {
			return "조금 전";
		}
	}
}
