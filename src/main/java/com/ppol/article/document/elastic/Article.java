package com.ppol.article.document.elastic;

import java.util.Date;
import java.util.List;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 	서비스의 검색 기능을 활용하기 위해 Elasticsearch에 간단한 게시글/피드 정보를 저장하는 Document
 * 	게시글/피드의 text 내용과 해쉬태그들을 저장하고 검색할 수 있도록한다.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Document(indexName = "article")
public class Article {

	// MariaDB에 저장되어있는 article의 key값과 동일하게 설정한다.
	@Id
	@Field(type = FieldType.Keyword)
	private Long id;

	// 내용으로 검색하는 용도
	@Field(type = FieldType.Text)
	private String content;

	// 사용자가 설정한 해쉬 태그들 (일치하는 해쉬태그를 검색하는 용도)
	@Field(type = FieldType.Keyword)
	private List<String> hashTags;

	// 검색 결과를 최신 순으로 정렬하기 위한 용도
	@Field(type = FieldType.Date, format = {}, pattern = "uuuu:MM:dd HH:mm:ss")
	private Date createdAt;

	public void updateContent(String content) {
		this.content = content;
	}

	public void updateHashTags(List<String> hashTags) {
		this.hashTags = hashTags;
	}
}
