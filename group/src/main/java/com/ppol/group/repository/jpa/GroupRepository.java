package com.ppol.group.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ppol.group.entity.group.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {

	@Query("SELECT g FROM Group g JOIN g.userList u WHERE u.id = :userId AND g.state = 0")
	List<Group> findByUserList_Id(@Param("userId") Long userId);

	int countByOwner_Id(Long userId);
}
