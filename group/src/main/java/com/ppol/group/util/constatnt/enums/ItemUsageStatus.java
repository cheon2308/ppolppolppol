package com.ppol.group.util.constatnt.enums;

import com.ppol.group.util.constatnt.enums.global.BasicEnum;

/**
 * 	각 3D 아이템의 사용여부를 나타내는 ENUM
 * 	USING : 사용 중
 * 	HOLDING : 보유 중
 */
public enum ItemUsageStatus implements BasicEnum {
	USING("using"), HOLDING("holding");

	private final String code;

	ItemUsageStatus(String code) {
		this.code = code;
	}

	@Override
	public String getCode() {
		return code;
	}
}
