package com.smirnov.carwashmessage.controller;

import com.smirnov.carwashmessage.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * Контроллер для отправки сообщений на email.
 */
@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
@Slf4j
public class EmailController {

    /**
     * Сервисный слой отправки сообщений на email.
     */
    private final EmailService emailService;


    /**
     * Отправляет сообщение для подтверждения записи по ее идентификатору.
     * @param userEmail email пользователя
     * @param id Идентификатор пользователя
     */
    @GetMapping("/{user-email}/{id}")
    public void sendApproveRecording(@PathVariable("user-email")String userEmail, @PathVariable("id") Integer id) {
        log.info("GET: /email/{}/{}", userEmail, id);
        emailService.sendMessageApprove(userEmail, id);
    }
}