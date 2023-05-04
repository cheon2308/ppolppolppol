package com.ppol.personal.util.constatnt.enums;

import com.ppol.personal.util.constatnt.enums.global.BasicEnum;

/**
 * 알람 참조에서 데이터가 어떤 데이터인지를 나타내는 ENUM
 */
public enum DataType implements BasicEnum {

	ARTICLE("article"), ARTICLE_COMMENT("article_comment"), USER("user"), ALBUM("album"), GROUP("group"), MESSAGE(
		"message"), MESSAGE_CHANNEL("message_channel"), GROUP_ARTICLE("group_article"), ALBUM_COMMENT("album_comment");

	private final String code;

	DataType(String code) {
		this.code = code;
	}

	@Override
	public String getCode() {
		return this.code;
	}
}