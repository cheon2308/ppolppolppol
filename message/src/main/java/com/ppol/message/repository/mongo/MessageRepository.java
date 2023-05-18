package com.ppol.message.repository.mongo;

import java.time.LocalDateTime;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.ppol.message.document.mongodb.Message;

public interface MessageRepository extends MongoRepository<Message, ObjectId> {

	Slice<Message> findByMessageChannelIdAndTimestampAfterOrderByTimestampDesc(ObjectId messageChannelId,
		LocalDateTime timestamp, Pageable pageable);

	Slice<Message> findByMessageChannelIdOrderByTimestampDesc(ObjectId messageChannelId, Pageable pageable);

	Optional<Message> findTopByMessageChannelIdOrderByTimestampDesc(ObjectId channelId);

	int countByMessageChannelIdAndTimestampAfter(ObjectId channelId, LocalDateTime timestamp);

	int countByMessageChannelId(ObjectId channelId);
}
