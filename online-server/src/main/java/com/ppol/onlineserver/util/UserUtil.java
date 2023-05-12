package com.ppol.onlineserver.util;

import java.util.HashMap;
import java.util.Map;

public class UserUtil {

	public record UserInfo(Long userId, Long groupId) {

	}

	public static Map<String, UserInfo> userMap = new HashMap<>();

	public static void put(String sessionId, Long userId, Long groupId) {
		userMap.put(sessionId, new UserInfo(userId, groupId));
	}

	public static boolean delete(String sessionId) {
		return userMap.remove(sessionId) != null;
	}

	public static long getUserId(String sessionId) {
		return userMap.get(sessionId).userId();
	}

	public static long getGroupId(String sessionId) {
		return userMap.get(sessionId).groupId();
	}
}
