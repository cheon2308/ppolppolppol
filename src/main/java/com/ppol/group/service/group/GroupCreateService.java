package com.ppol.group.service.group;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ppol.group.document.mongodb.MessageChannel;
import com.ppol.group.document.mongodb.MessageUser;
import com.ppol.group.dto.request.group.GroupCreateDto;
import com.ppol.group.dto.response.group.GroupDetailDto;
import com.ppol.group.entity.group.Group;
import com.ppol.group.entity.user.User;
import com.ppol.group.exception.exception.UserGroupLimitException;
import com.ppol.group.repository.jpa.GroupRepository;
import com.ppol.group.repository.mongo.MessageChannelRepository;
import com.ppol.group.service.user.UserReadService;
import com.ppol.group.util.constatnt.classes.ValidationConstants;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 그룹을 생성하는 기능들을 제공하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GroupCreateService {

	// repository
	private final GroupRepository groupRepository;
	private final MessageChannelRepository channelRepository;

	// services
	private final GroupReadService groupReadService;
	private final UserReadService userReadService;

	/**
	 * 새로운 그룹을 생성하는 메서드
	 */
	@Transactional
	public GroupDetailDto createGroup(Long userId, GroupCreateDto groupCreateDto) {

		if (!checkCapacity(userId)) {
			throw new UserGroupLimitException();
		} else {
			User owner = userReadService.getUser(userId);

			Group group = groupRepository.save(Group.builder().title(groupCreateDto.getTitle()).owner(owner).build());

			group.addUser(owner);

			createMessageChannel(group);

			return GroupDetailDto.of(group);
		}
	}

	/**
	 * 그룹에 해당하는 새로운 메시지 채널 생성하는 메서드
	 */
	private void createMessageChannel(Group group) {

		channelRepository.save(MessageChannel.builder()
			.groupId(group.getId())
			.title(group.getTitle())
			.userList(group.getUserList().stream().map(MessageUser::of).collect(Collectors.toSet()))
			.lastMessageTimestamp(LocalDateTime.now())
			.build());
	}

	/**
	 * 해당 유저가 생성한 그룹의 수 체크해서 더 생성할 수 있는지 여부를 반환하는 메서드
	 */
	private boolean checkCapacity(Long userId) {
		return groupRepository.countByOwner_Id(userId) < ValidationConstants.USER_CREATE_GROUP_MAX;
	}

}
