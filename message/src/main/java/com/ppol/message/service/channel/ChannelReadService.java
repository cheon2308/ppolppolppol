package com.ppol.message.service.channel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ppol.message.document.mongodb.Message;
import com.ppol.message.document.mongodb.MessageChannel;
import com.ppol.message.document.mongodb.MessageUser;
import com.ppol.message.dto.response.ChannelResponseDto;
import com.ppol.message.exception.exception.ForbiddenException;
import com.ppol.message.repository.mongo.MessageChannelRepository;
import com.ppol.message.service.user.UserReadService;
import com.ppol.message.service.message.MessageReadService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 메시지 채널을 불러오는 기능들을 제공하는 서비스
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ChannelReadService {

	// repositories
	private final MessageChannelRepository channelRepository;

	// services
	private final UserReadService userReadService;
	private final MessageReadService messageReadService;

	/**
	 * 1대1 Direct 채팅방을 찾는 메서드
	 */
	public ChannelResponseDto directChannelRead(Long userId, Long receiver) {

		MessageChannel channel = channelRepository.findByTwoUsersInUserListAndGroupIdNull(userId, receiver)
			.orElse(null);

		channel = channel == null ? channelRepository.save(makeNewDirectChannel(userId, receiver)) : channel;

		return channelMapping(channel, userId);
	}

	/**
	 * 그룹 채팅방을 찾는 메서드
	 */
	public ChannelResponseDto groupChannelRead(Long userId, Long groupId) {

		return channelMapping(
			channelRepository.findByGroupId(groupId).orElseThrow(() -> new EntityNotFoundException("그룹 채팅방")), userId);
	}

	/**
	 * 사용자가 참여중인 채팅방 목록을 List 형태로 받아오는 메서드
	 */
	public List<ChannelResponseDto> channelListRead(Long userId) {

		Sort sort = Sort.by(Sort.Direction.DESC, "lastMessageTimestamp");

		return channelRepository.findByUserIdInUserList(userId, sort)
			.stream()
			.map(channel -> channelListMapping(channel, userId))
			.toList();
	}

	/**
	 * 엔티티를 불러오는 기본 메서드, 예외처리를 포함한다.
	 */
	public MessageChannel getMessageChannel(String channelId) {
		return channelRepository.findById(new ObjectId(channelId))
			.orElseThrow(() -> new EntityNotFoundException("채팅 채널"));
	}

	/**
	 * 새로운 1대1 Direct Channel을 만들어주는 메서드
	 */
	private MessageChannel makeNewDirectChannel(Long userId, Long receiverId) {
		Set<MessageUser> userList = new HashSet<>();

		userList.add(userReadService.findUser(userId));
		userList.add(userReadService.findUser(receiverId));

		return MessageChannel.builder().userList(userList).build();
	}

	/**
	 * 채팅방 목록 DTO에 부가 데이터들을 (읽지않은 메시지 수, 최근 메시지) 설정해주는 메서드
	 */
	public ChannelResponseDto channelListMapping(MessageChannel channel, Long userId) {

		Message newMessage = messageReadService.getNewMessage(channel.getId());
		String newContent = newMessage == null ? "" : newMessage.getContent();

		ObjectId messageId = channel.getUserList()
			.stream()
			.filter(user -> user.getUserId().equals(userId))
			.findAny()
			.orElseThrow(() -> new ForbiddenException("채널"))
			.getLastReadMessageId();

		int newMessageCount = messageReadService.getNewMessageCount(channel.getId(), messageId);

		ChannelResponseDto responseDto = channelMapping(channel, userId);

		responseDto.setNewContent(newContent);
		responseDto.setNewMessageCount(newMessageCount);
		responseDto.setLastMessageTimestamp(newMessage == null ? null : newMessage.getTimestamp());

		return responseDto;
	}

	/**
	 * 채팅방을 DTO로 매핑하는 메서드
	 */
	public ChannelResponseDto channelMapping(MessageChannel channel, Long userId) {

		return channel.getGroupId() == null ? directChannelMapping(channel, userId) : groupChannelMapping(channel);
	}

	/**
	 * 다이렉트 채팅방을 DTO로 매핑하는 메서드
	 */
	private ChannelResponseDto directChannelMapping(MessageChannel channel, Long userId) {

		String title = channel.getUserList()
			.stream()
			.filter(messageUser -> !messageUser.getUserId().equals(userId))
			.findAny()
			.orElseThrow(() -> new EntityNotFoundException("?"))
			.getUsername();

		return ChannelResponseDto.builder()
			.channelId(channel.getId().toString())
			.title(title)
			.groupId(null)
			.userList(channel.getUserList())
			.lastMessageTimestamp(channel.getLastMessageTimestamp())
			.build();
	}

	/**
	 * 그룹 채팅방을 DTO로 매핑하는 메서드
	 */
	private ChannelResponseDto groupChannelMapping(MessageChannel channel) {

		return ChannelResponseDto.builder()
			.channelId(channel.getId().toString())
			.title(channel.getTitle())
			.groupId(channel.getGroupId())
			.userList(channel.getUserList())
			.lastMessageTimestamp(channel.getLastMessageTimestamp())
			.build();
	}
}
