package com.ppol.onlineserver.util;

import java.util.HashMap;
import java.util.Map;

import com.ppol.onlineserver.dto.response.OxLobbyDto;

public class OxLobbyUtil {

	public static Map<String, OxLobbyDto> oxLobbyMap = new HashMap<>();

	public static void put(String groupId, OxLobbyDto oxLobbyDto) {
		oxLobbyMap.put(groupId, oxLobbyDto);
	}

	public static boolean delete(String groupId) {
		return oxLobbyMap.remove(groupId) != null;
	}

	public static OxLobbyDto getOxLobby(String groupId) {
		return oxLobbyMap.get(groupId);
	}
}
