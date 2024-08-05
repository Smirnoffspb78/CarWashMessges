package com.smirnov.carwashmessage.configuration;

import com.smirnov.carwashmessage.dto.request.MessageRecordingDTO;
import com.smirnov.carwashmessage.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@EnableRabbit
@Slf4j
@RequiredArgsConstructor
public class RabbitMqListener {

    private final EmailService emailService;

    @RabbitListener(queues = "carWashQueue", ackMode = "MANUAL")
    public Mono<Void> processMyQueue(MessageRecordingDTO messageRecordingDTO) {
        log.info("Сообщение от брокера");
        return emailService.sendMessageApprove(messageRecordingDTO.email(), messageRecordingDTO.userId());
    }
}
