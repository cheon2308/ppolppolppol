package com.ppol.group.repository.mongo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.ppol.group.document.mongodb.MessageChannel;

public interface MessageChannelRepository extends MongoRepository<MessageChannel, ObjectId> {
}
