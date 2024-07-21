package com.smirnov.carwashmessage.repository;

import com.smirnov.carwashmessage.entity.Message;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

/**
 * Репозиторий сообщений.
 */
public interface MessageRepository extends ReactiveMongoRepository<Message, String> {

    /**
     * Возвращает сообщение по наименованию текста.
     * @param name Имя сообщения.
     * @return Сообщение
     */
    @Query(value = "{name :  ?0}", fields = "{text: 1, subject: 2}")
    Mono<Message> messageByName(String name);
}
