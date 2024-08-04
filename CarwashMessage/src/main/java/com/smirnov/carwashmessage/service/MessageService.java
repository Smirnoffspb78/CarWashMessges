package com.smirnov.carwashmessage.service;

import com.smirnov.carwashmessage.exception.MessageNotFoundException;
import com.smirnov.carwashmessage.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * Сервисный слой сообщений.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {

    /**
     * Репозиторий сообщений.
     */
    private final MessageRepository messageRepository;

    public Mono<MessageDTO> getById(String id){
        Mono<MessageDTO> messageDTOMono = messageRepository.findById(id)
                .flatMap(message -> Mono.just(new MessageDTO(message.getText(), message.getSubject())))
                .switchIfEmpty(Mono.defer(() ->Mono.error(() -> {
                    log.error("Отсутствует сообщение с именем %s".formatted(id));
                    return new MessageNotFoundException("Отсутствует сообщение с именем ");})))
                .timeout(Duration.ofSeconds(5));
        log.info("Шаблон сообщения с id {} получен из БД", id);
        return messageDTOMono;
    }
}
