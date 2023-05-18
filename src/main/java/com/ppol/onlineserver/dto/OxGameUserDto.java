package com.ppol.onlineserver.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotNull;
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
public class OxGameUserDto implements Serializable {

	@NotNull
	private Long userId;

	private String username;

	private int answerCount;

	public void addCount() {
		this.answerCount++;
	}
}
