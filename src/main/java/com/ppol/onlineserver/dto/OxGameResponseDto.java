package com.ppol.onlineserver.dto;

import java.io.Serializable;

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
public class OxGameResponseDto implements Serializable {

	private OxGameDto oxGame;
	private String nextQuestion;
	private String commentary;
}
