package com.ppol.onlineserver.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MoveRequestDto {

	public Long cId;
	public Double x;
	public Double y;
	public Double z;
	public Double rx;
	public Double ry;
	public Double rz;
	public String state;
}
