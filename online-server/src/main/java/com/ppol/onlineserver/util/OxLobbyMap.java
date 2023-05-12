package com.ppol.onlineserver.util;

import java.util.HashMap;
import java.util.Map;

import com.ppol.onlineserver.dto.response.OxLobbyDto;

public class OxLobbyMap {

	public static Map<Long, OxLobbyDto> oxLobbyMap = new HashMap<>();

	public static void put(Long groupId, OxLobbyDto oxLobbyDto) {
		oxLobbyMap.put(groupId, oxLobbyDto);
	}

	public static boolean delete(Long groupId) {
		return oxLobbyMap.remove(groupId) != null;
	}

	public static OxLobbyDto getOxLobby(Long groupId) {
		return oxLobbyMap.get(groupId);
	}
}
