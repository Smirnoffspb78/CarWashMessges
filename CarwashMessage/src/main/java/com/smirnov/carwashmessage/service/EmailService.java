package com.smirnov.carwashmessage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Сервисный слой отправки сообщений.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    /**
     * Интерфейс, поддерживающий отправку сообщений
     */
    private final JavaMailSender mailSender;

    /**
     * Сервисный слой сообщений
     */
    private final MessageService messageService;

    private static final String ID = "approve-record";
    private static final String EMAIL_SENDER = "admcarwash@yandex.ru";

    public Mono<Void> sendMessageApprove(String email, Integer id) {
        messageService.getById(ID).map(messageDTO -> {
                    Mono.fromRunnable(() -> {
                                try {
                                    SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                                    simpleMailMessage.setFrom(EMAIL_SENDER);
                                    simpleMailMessage.setTo(email);
                                    simpleMailMessage.setSubject(messageDTO.subject());
                                    simpleMailMessage.setText(messageDTO.text().formatted(id));
                                    mailSender.send(simpleMailMessage);
                                } catch (MailParseException e) {
                                    log.error("Неверный формат сообщения");
                                } catch (MailAuthenticationException e) {
                                    log.error("Неверная комбинация email И пароля отправителя");
                                } catch (MailSendException e) {
                                    log.error("Не удалось отправить сообщение. Проверьте правильность email получателя");
                                }
                        log.info("Сообщение отправлено на email {}", email);
                            }
                    ).subscribe();
                    return Mono.empty();
                }
        ).subscribe();
        return Mono.empty();
    }
}