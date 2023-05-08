package com.ppol.alarm.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ppol.alarm.entity.alarm.AlarmReference;

public interface AlarmReferenceRepository extends JpaRepository<AlarmReference, Long> {

	List<AlarmReference> findByAlarm_Id(Long alarmId);
}
