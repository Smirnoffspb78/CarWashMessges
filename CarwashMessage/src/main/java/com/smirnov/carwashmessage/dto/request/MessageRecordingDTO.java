package com.smirnov.carwashmessage.dto.request;

import java.io.Serializable;

/**
 * DTO для отправки сообщения пользователю на почту
 * @param userId Идентификатор пользователя
 * @param email Электронная почта
 */
public record MessageRecordingDTO(Integer userId, String email) implements Serializable {
}