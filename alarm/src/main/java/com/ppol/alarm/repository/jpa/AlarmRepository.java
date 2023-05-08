package com.ppol.alarm.repository.jpa;

import java.time.LocalDateTime;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ppol.alarm.entity.alarm.Alarm;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

	Slice<Alarm> findByUser_IdAndCreatedAtBeforeOrderByCreatedAtDesc(Long userId, LocalDateTime timestamp,
		Pageable pageable);
}
