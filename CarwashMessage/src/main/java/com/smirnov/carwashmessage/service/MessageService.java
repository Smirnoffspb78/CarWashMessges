package com.smirnov.carwashmessage.service;

import com.smirnov.carwashmessage.dto.request.MessageDTO;
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

    public Mono<MessageDTO> getMessageByName(String name) {
            Mono<MessageDTO> messageDTOMono = messageRepository.messageByName(name)
                    .flatMap(message -> Mono.just(new MessageDTO(message.getText(), message.getSubject())))
                    .switchIfEmpty(Mono.defer(() ->Mono.error(() -> {
                        log.error("Отсутствует сообщение с именем %s".formatted(name));
                        return new MessageNotFoundException("Отсутствует сообщение с именем ");})))
                    .timeout(Duration.ofSeconds(5));
            log.info("Получено сообщение названием {}", name);
            return messageDTOMono;
    }
}
