package com.ppol.onlineserver.util.constant.enums;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.ppol.onlineserver.exception.exception.EnumConvertException;

public enum EventType {

	ENTER, MOVE, TYPE_UPDATE, LEAVE, ENTER_LOBBY, LEAVE_LOBBY, MAKE_LOBBY, DESTROY_LOBBY, START_OX;

	@JsonValue
	public String to() {
		return this.name();
	}

	@JsonCreator
	public static EventType from(String name) {

		return Arrays.stream(EventType.values())
			.filter(eventType -> eventType.name().equals(name))
			.findAny()
			.orElseThrow(() -> new EnumConvertException("EventType", name));
	}
}
