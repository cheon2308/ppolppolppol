package com.ppol.alarm.entity.alarm;

import com.ppol.alarm.entity.global.BaseEntity;
import com.ppol.alarm.util.constatnt.enums.DataType;
import com.ppol.alarm.util.converter.DataTypeConverter;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 	서비스의 알람에서 참조해야할 데이터를 저장하는 엔티티
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class AlarmReference extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 어떤 알람의 참조 데이터인지
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private Alarm alarm;

	// 알람에서 해당 데이터가 몇 번째 {?}에 해당하는지
	@NotNull
	private Integer referenceId;

	// 알람에서 실제 표시될 데이터
	@NotNull
	private String data;

	// 해당 오브젝트의 DB에서의 id 값
	@NotNull
	private Long targetId;

	// 해당 오브젝트의 종류
	@NotNull
	@Convert(converter = DataTypeConverter.class)
	private DataType type;
}
