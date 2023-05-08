package com.ppol.user.dto.feign;

import com.ppol.user.entity.user.User;
import com.ppol.user.util.constatnt.enums.DataType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AlarmReferenceDto {

	private int referenceId;
	private String data;
	private Long targetId;
	private DataType type;

	public static AlarmReferenceDto of(User user, int referenceId) {
		return AlarmReferenceDto.builder()
			.referenceId(referenceId)
			.data(cutString(user.getUsername()))
			.targetId(user.getId())
			.type(DataType.USER)
			.build();
	}

	private static String cutString(String content) {

		int maxSize = 10;

		return content.length() <= maxSize ? content : content.substring(0, maxSize) + "..";
	}
}