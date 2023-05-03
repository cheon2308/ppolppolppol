package com.ppol.group.service.invite;

import org.springframework.stereotype.Service;

import com.ppol.group.document.mongodb.MessageChannel;
import com.ppol.group.dto.request.invite.InviteAnswerDto;
import com.ppol.group.dto.request.invite.InviteCreateDto;
import com.ppol.group.dto.response.InviteResponseDto;
import com.ppol.group.entity.group.Group;
import com.ppol.group.entity.group.GroupInvite;
import com.ppol.group.entity.group.GroupUserAlarm;
import com.ppol.group.entity.user.User;
import com.ppol.group.exception.exception.GroupMemberExceededException;
import com.ppol.group.repository.GroupInviteRepository;
import com.ppol.group.repository.jpa.GroupUserAlarmRepository;
import com.ppol.group.repository.mongo.MessageChannelRepository;
import com.ppol.group.service.alarm.AlarmSendService;
import com.ppol.group.service.group.GroupReadService;
import com.ppol.group.service.user.UserReadService;
import com.ppol.group.util.constatnt.classes.ValidationConstants;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 그룹에 다른 사용자를 초대하고, 초대받은 사용자가 응답하는 기능들을 제공하는 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class InviteService {

	// repository
	private final GroupInviteRepository inviteRepository;
	private final GroupUserAlarmRepository alarmRepository;
	private final MessageChannelRepository messageChannelRepository;

	// service
	private final GroupReadService groupReadService;
	private final UserReadService userReadService;
	private final AlarmSendService alarmSendService;

	/**
	 * 사용자를 초대해서 초대 엔티티를 생성하고, 초대받은 사용자에겐 알람을 날리는 메서드
	 * 이 때 그룹의 인원 수가 정원을 넘는지 확인하는 로직이 포함됨
	 */
	@Transactional
	public InviteResponseDto createInvite(Long groupId, Long userId, InviteCreateDto inviteCreateDto) {

		Group group = groupReadService.getGroupWithOwner(groupId, userId);

		if (!checkCapacity(group)) {
			throw new GroupMemberExceededException();
		}

		User user = userReadService.getUser(userId);
		User targetUser = userReadService.getUser(inviteCreateDto.getTargetUserId());

		GroupInvite invite = inviteRepository.save(GroupInvite.builder().group(group).user(targetUser).build());

		alarmSendService.createInviteAlarm(group, user, targetUser.getId());

		return InviteResponseDto.of(invite);
	}

	/**
	 * 초대에 대한 사용자의 응답을 처리하는 메서드
	 * 승인했을 경우 그룹과 채팅채널에 추가하는데 이 때 그룹의 인원 수가 정원을 넘는지 확인하는 로직이 포함됨
	 * 두 경우 모두 초대 객체는 삭제 처리함
	 */
	@Transactional
	public String answerInvite(Long groupId, Long userId, InviteAnswerDto inviteAnswerDto) {

		GroupInvite invite = getInvite(userId, groupId);

		String returnString = "거절함";

		if (inviteAnswerDto.getAccept()) {
			Group group = groupReadService.getGroup(groupId);

			if (!checkCapacity(group)) {
				throw new GroupMemberExceededException();
			}

			User user = userReadService.getUser(userId);

			group.addUser(user);
			alarmRepository.save(GroupUserAlarm.builder().group(group).user(user).build());

			MessageChannel channel = messageChannelRepository.findByGroupId(groupId)
				.orElseThrow(() -> new EntityNotFoundException("메시지/채팅 채널"));

			channel.addUser(user);

			alarmSendService.createUserEnterAlarm(group, user);

			returnString = "참여함";
		}

		invite.delete();

		return returnString;
	}

	/**
	 * 초대 Entity를 불러오는 메서드 예외처리 포함
	 */
	public GroupInvite getInvite(Long userId, Long groupId) {
		return inviteRepository.findByUser_IdAndGroup_Id(userId, groupId)
			.orElseThrow(() -> new EntityNotFoundException("그룹 초대"));
	}

	/**
	 * 그룹의 인원 수가 최대 인원 수 보다 작은지 확인하는 메서드
	 */
	private boolean checkCapacity(Group group) {
		return group.getUserList().size() < ValidationConstants.GROUP_USER_MAX;
	}
}
