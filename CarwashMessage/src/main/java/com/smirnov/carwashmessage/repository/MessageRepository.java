package com.smirnov.carwashmessage.repository;

import com.smirnov.carwashmessage.entity.Message;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Репозиторий сообщений.
 */
public interface MessageRepository extends ReactiveMongoRepository<Message, String> {
}
