package com.ppol.onlineserver.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

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
public class OxGameDto implements Serializable {

	private int problemNum;
	private int problemSec;
	private Set<OxGameUserDto> oxPlayers;
	private List<Long> previousQuestions;
	private int playerCount;
}
