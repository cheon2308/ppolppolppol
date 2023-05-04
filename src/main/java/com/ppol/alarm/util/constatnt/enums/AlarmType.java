package com.ppol.alarm.util.constatnt.enums;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.ppol.alarm.exception.exception.InvalidAlarmCodeException;
import com.ppol.alarm.util.constatnt.classes.AlarmContent;
import com.ppol.alarm.util.constatnt.enums.global.BasicEnum;

/**
 * 알람의 종류를 나타내는 ENUM
 */
public enum AlarmType implements BasicEnum {

	// 누군가 내 게시글에 좋아요
	ARTICLE_LIKE("100", AlarmContent.ARTICLE_LIKE),
	// 누군가 내 게시글에 댓글
	ARTICLE_COMMENT("101", AlarmContent.ARTICLE_COMMENT),
	// 누군가 내 댓글에 답글
	COMMENT_REPLY("110", AlarmContent.COMMENT_REPLY),
	// 누군가 내 댓글 좋아요
	COMMENT_LIKE("111", AlarmContent.COMMENT_LIKE),
	// 누군가 댓글에 나를 태그
	COMMENT_TAG("120", AlarmContent.COMMENT_TAG),
	// 누군가 내 개인룸에 접속
	PERSONAL_ROOM_IN("200", AlarmContent.PERSONAL_ROOM_IN),
	// 누군가 내 앨범에 댓글
	ALBUM_COMMENT("210", AlarmContent.ALBUM_COMMENT),
	// 누군가 그룹에 나를 초대함
	GROUP_INVITE("300", AlarmContent.GROUP_INVITE),
	// 그룹에 새로운 사용자 참여
	GROUP_NEW_USER("301", AlarmContent.GROUP_NEW_USER),
	// 그룹에 새로운 게시글/피드
	GROUP_NEW_ARTICLE("310", AlarmContent.GROUP_NEW_ARTICLE),
	// 누군가 나에게 새로운 채팅 메시지를 보냄
	NEW_MESSAGE("400", AlarmContent.NEW_MESSAGE),
	// 누군가 나를 팔로우
	FOLLOW("500", AlarmContent.FOLLOW),
	// 팔로우한 유저가 새로운 게시글 작성
	FOLLOWING_NEW_ARTICLE("501", AlarmContent.FOLLOWING_NEW_ARTICLE);

	private final String code;
	private final String message;

	AlarmType(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

	@JsonValue
	public String getCode() {
		return this.code;
	}

	@JsonCreator
	public static AlarmType of(String code) {
		return Arrays.stream(AlarmType.values())
			.filter(alarmType -> alarmType.getCode().equals(code))
			.findAny()
			.orElseThrow(InvalidAlarmCodeException::new);
	}
}
