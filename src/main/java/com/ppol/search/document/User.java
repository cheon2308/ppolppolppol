package com.ppol.search.document;

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
 * 	서비스의 검색 기능을 활용하기 위해 Elasticsearch에 간단한 사용자 정보를 저장하는 Document
 * 	사용자의 username으로 검색하기 위해 해당 정보를 저장한다.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Document(indexName = "user")
public class User {

	// MariaDB에 저장되어있는 user의 key값과 동일하게 설정한다.
	@Id
	@Field(type = FieldType.Keyword)
	private Long id;

	// 사용자의 이름/닉네임으로 검색하는 용도
	@Field(type = FieldType.Text)
	private String username;
}