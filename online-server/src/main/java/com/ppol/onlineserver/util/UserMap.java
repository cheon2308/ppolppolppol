package com.ppol.onlineserver.util;

import java.util.HashMap;
import java.util.Map;

public class UserMap {

	public record UserInfo(Long userId, Long groupId) {

	}

	public static Map<String, UserInfo> userMap = new HashMap<>();

	public static void put(String sessionId, Long userId, Long groupId) {
		userMap.put(sessionId, new UserInfo(userId, groupId));
	}

	public static void delete(String sessionId) {
		userMap.remove(sessionId);
	}

	public static long getUserId(String sessionId) {
		return userMap.get(sessionId).userId();
	}

	public static long getGroupId(String sessionId) {
		return userMap.get(sessionId).groupId();
	}
}
