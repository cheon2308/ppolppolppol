package com.ppol.group.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ppol.group.entity.group.GroupUserAlarm;

public interface GroupUserAlarmRepository extends JpaRepository<GroupUserAlarm, Long> {

	List<GroupUserAlarm> findByGroup_IdAndAlarmOn(Long groupId, boolean alarmOn);

	void deleteByGroup_IdAndUser_Id(Long groupId, Long userId);
}
