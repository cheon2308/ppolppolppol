package com.ppol.article.document.elastic;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Document(indexName = "article")
public class Article {

	@Id
	@Field(type = FieldType.Keyword)
	private Long id;

	@Field(type = FieldType.Text)
	private String content;

	@Field(type = FieldType.Keyword)
	private List<String> hashTags;
}
