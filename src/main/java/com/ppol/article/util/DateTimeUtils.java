package com.ppol.article.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.ppol.article.util.constatnt.classes.DateTimeFormatString;

public class DateTimeUtils {

	public static String getString(LocalDateTime timestamp) {
		return getString(timestamp, DateTimeFormatString.BASIC);
	}

	public static String getString(LocalDateTime timestamp, String format) {
		LocalDateTime now = LocalDateTime.now();
		Duration duration = Duration.between(timestamp, now);

		long days = duration.toDays();
		long hours = duration.toHours();
		long minutes = duration.toMinutes();

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
