package com.ppol.article.dto.feign;



import com.ppol.article.entity.article.Article;
import com.ppol.article.entity.article.ArticleComment;
import com.ppol.article.entity.user.User;
import com.ppol.article.util.constatnt.enums.DataType;

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

	public static AlarmReferenceDto of(ArticleComment comment, int referenceId) {
		return AlarmReferenceDto.builder()
			.referenceId(referenceId)
			.data(cutString(comment.getContent()))
			.targetId(comment.getId())
			.type(DataType.ARTICLE_COMMENT)
			.build();
	}

	public static AlarmReferenceDto of(Article article, int referenceId) {
		return AlarmReferenceDto.builder()
			.referenceId(referenceId)
			.data(cutString(article.getContent()))
			.targetId(article.getId())
			.type(DataType.ARTICLE)
			.build();
	}

	private static String cutString(String content) {

		int maxSize = 10;

		return content.length() <= maxSize ? content : content.substring(0, maxSize) + "..";
	}
}