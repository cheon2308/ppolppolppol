package com.ppol.group.service.user;

import org.springframework.stereotype.Service;

import com.ppol.group.entity.group.Group;
import com.ppol.group.entity.user.User;
import com.ppol.group.repository.jpa.GroupUserAlarmRepository;
import com.ppol.group.repository.mongo.MessageChannelRepository;
import com.ppol.group.service.group.GroupReadService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 그룹에서 사용자를 삭제하는 기능을 담당하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserDeleteService {

	// repository
	private final GroupUserAlarmRepository alarmRepository;
	private final MessageChannelRepository messageChannelRepository;

	// services
	private final GroupReadService groupReadService;
	private final UserReadService userReadService;

	/**
	 * 특정 그룹에서 사용자를 삭제하는 메서드
	 */
	@Transactional
	public void deleteUserFromGroup(Long userId, Long groupId) {

		Group group = groupReadService.getGroup(groupId);
		User user = userReadService.getUser(userId);

		group.getUserList().remove(user);
		alarmRepository.deleteByGroup_IdAndUser_Id(groupId, userId);

		messageChannelRepository.findByGroupId(groupId)
			.orElseThrow(() -> new EntityNotFoundException("메시지/채팅 채널"))
			.deleteUser(userId);

		if (group.getUserList().size() == 0) {
			group.delete();
		} else if (group.getOwner().equals(user)) {
			group.updateOwner(group.getUserList().stream().findFirst().orElseThrow());
		}
	}
}
