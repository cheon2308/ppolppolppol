package com.ppol.personal.dto.feign;

import com.ppol.personal.entity.personal.Album;
import com.ppol.personal.entity.personal.AlbumComment;
import com.ppol.personal.entity.user.User;
import com.ppol.personal.util.constatnt.enums.DataType;

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

	public static AlarmReferenceDto of(AlbumComment comment, int referenceId) {
		return AlarmReferenceDto.builder()
			.referenceId(referenceId)
			.data(cutString(comment.getContent()))
			.targetId(comment.getId())
			.type(DataType.ALBUM_COMMENT)
			.build();
	}

	public static AlarmReferenceDto of(Album album, int referenceId) {
		return AlarmReferenceDto.builder()
			.referenceId(referenceId)
			.data(cutString(album.getTitle()))
			.targetId(album.getId())
			.type(DataType.ALBUM_COMMENT)
			.build();
	}

	private static String cutString(String content) {

		int maxSize = 10;

		return content.length() <= maxSize ? content : content.substring(0, maxSize) + "..";
	}
}