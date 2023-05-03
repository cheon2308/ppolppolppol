package com.ppol.group.dto.request.alarm;

import com.ppol.group.entity.group.Group;
import com.ppol.group.entity.group.GroupArticle;
import com.ppol.group.entity.user.User;
import com.ppol.group.util.constatnt.enums.DataType;

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
			.data(user.getUsername())
			.targetId(user.getId())
			.type(DataType.USER)
			.build();
	}

	public static AlarmReferenceDto of(Group group, int referenceId) {
		return AlarmReferenceDto.builder()
			.referenceId(referenceId)
			.data(group.getTitle())
			.targetId(group.getId())
			.type(DataType.GROUP)
			.build();
	}

	public static AlarmReferenceDto of(GroupArticle groupArticle, int referenceId) {
		return AlarmReferenceDto.builder()
			.referenceId(referenceId)
			.data(groupArticle.getContent())
			.targetId(groupArticle.getId())
			.type(DataType.GROUP_ARTICLE)
			.build();
	}
}
