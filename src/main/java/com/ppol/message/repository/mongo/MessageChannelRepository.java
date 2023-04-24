package com.ppol.message.repository.mongo;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ppol.message.document.mongodb.MessageChannel;

public interface MessageChannelRepository extends MongoRepository<MessageChannel, ObjectId> {

	@Query("{'userList.userId': ?0}")
	List<MessageChannel> findByUserIdInUserList(Long userId);

	@Query("{'userList.id': {$all: [?0, ?1]}, 'groupId': null}")
	Optional<MessageChannel> findByTwoUsersInUserListAndGroupIdNull(Long userId, Long receiverId);

	Optional<MessageChannel> findByGroupId(Long groupId);
}
