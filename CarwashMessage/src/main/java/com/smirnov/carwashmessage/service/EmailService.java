package com.smirnov.carwashmessage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    public void sendMessageApprove(String email, Integer id) {
        messageService.getMessageByName("approve_record").flatMap(messageDTO -> {
                    Mono.fromCallable(() -> {
                                        try {
                                            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                                            String mail = "admcarwash@yandex.ru";
                                            simpleMailMessage.setFrom(mail);
                                            simpleMailMessage.setTo(email);
                                            simpleMailMessage.setSubject(messageDTO.subject());
                                            simpleMailMessage.setText(messageDTO.text().formatted(id));
                                            mailSender.send(simpleMailMessage);
                                        } catch (MailParseException e){
                                            log.error("Неверный формат сообщения");
                                            return false;
                                        }
                                        catch (MailAuthenticationException e){
                                            log.error("Неверная комбинация email И пароля отправителя");
                                            return false;
                                        }
                                        catch (MailSendException e) {
                                            log.error("Не удалось отправить сообщение. Проверьте правильность email получателя");
                                            return false;
                                        }
                                        log.info("Сообщение отправлено на email {}", email);
                                        return true;
                                    }
                            )
                            .subscribe();
                    return Mono.empty();
                }
        ).subscribe();
    }
}